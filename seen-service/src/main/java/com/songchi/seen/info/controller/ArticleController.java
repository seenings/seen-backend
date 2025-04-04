package com.songchi.seen.info.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.songchi.seen.common.model.R;
import com.songchi.seen.common.util.ResUtils;
import com.songchi.seen.sys.util.ListUtils;
import com.songchi.seen.info.http.HttpUserMainPhotoService;
import com.songchi.seen.introduce.enumeration.IntroduceTypeEnum;
import com.songchi.seen.introduce.http.HttpUserIntroducePhotoService;
import com.songchi.seen.introduce.model.IntroduceTypeAndPhoto;
import com.songchi.seen.photo.http.HttpPhotoService;
import com.songchi.seen.sys.constant.PublicConstant;

import cn.hutool.core.lang.Pair;
import jakarta.annotation.Resource;

/**
 * ArticleController
 *
 * @author chixuehui
 * @since 2023-01-22
 */
@RestController
@RequestMapping(PublicConstant.REST + "user/article")
public class ArticleController {
    @Resource
    private HttpUserIntroducePhotoService httpUserIntroducePhotoService;

    @Resource
    private HttpPhotoService httpPhotoService;

    @Resource
    private HttpUserMainPhotoService httpUserMainPhotoService;

    @PostMapping("user-id-to-primary-photo-id")
    public R<Map<Long, List<Integer>>> userIdToPrimaryPhotoId(@RequestBody Set<Long> userIds) {
        Map<Long, Set<IntroduceTypeAndPhoto>> userIdToIntroduceTypeAndPhotoMap = httpUserIntroducePhotoService
                .userIdToIntroduceTypeAndPhoto(userIds);
        Map<Long, Integer> userIdMainPhotoIdMap = httpUserMainPhotoService.userIdPhotoId(userIds);
        Map<Long, List<Integer>> userIdToMainPagePhotoIdMap = userIdToIntroduceTypeAndPhotoMap.entrySet().stream()
                .map(entry -> {
                    Set<IntroduceTypeAndPhoto> introduceTypeAndPhotos = entry.getValue();
                    List<Integer> photoIds = introduceTypeAndPhotos.stream()
                            .filter(n -> n.introduceTypeEnum() == IntroduceTypeEnum.MAIN_PAGE)
                            .map(IntroduceTypeAndPhoto::photoId).collect(Collectors.toList());
                    return Pair.of(entry.getKey(), photoIds);
                }).collect(Collectors.toMap(Pair::getKey, Pair::getValue));
        Map<Long, List<Integer>> result = userIds.stream().map(userId -> {
            Integer mainPhotoId = userIdMainPhotoIdMap.get(userId);
            List<Integer> mainPagePhotoIds = userIdToMainPagePhotoIdMap.get(userId);
            List<Integer> primaryPhotoIds = ListUtils.union(Collections.singletonList(mainPhotoId), mainPagePhotoIds);
            return Pair.of(userId, primaryPhotoIds);
        }).collect(Collectors.toMap(Pair::getKey, Pair::getValue));
        return ResUtils.ok(result);
    }

    @PostMapping("user-id-to-introduce-type-and-photo-url")
    public R<Map<Long, Set<IntroduceTypeAndPhoto>>> userIdToIntroduceTypeAndPhotoUrl(
            @RequestBody Set<Long> userIds) {
        Map<Long, Set<IntroduceTypeAndPhoto>> userIdToIntroduceTypeAndPhotoMap = httpUserIntroducePhotoService
                .userIdToIntroduceTypeAndPhoto(userIds);
        Map<Long, Set<IntroduceTypeAndPhoto>> collect = userIdToIntroduceTypeAndPhotoMap.entrySet().stream()
                .parallel().map((Map.Entry<Long, Set<IntroduceTypeAndPhoto>> n) -> {
                    Long key = n.getKey();
                    Set<IntroduceTypeAndPhoto> introduceTypeAndPhotoUrls = n.getValue();
                    return Map.entry(key, introduceTypeAndPhotoUrls);
                }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (o1, o2) -> o2));
        return ResUtils.ok(collect);
    }
}
