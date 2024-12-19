package com.songchi.seen.introduce.service.impl;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.songchi.seen.introduce.enumeration.IntroduceTypeEnum;
import com.songchi.seen.introduce.model.IntroduceTypeAndPhoto;
import com.songchi.seen.introduce.model.IntroduceTypeAndPhotoInfo;
import com.songchi.seen.introduce.service.IntroducePhotoService;
import com.songchi.seen.introduce.service.UserIntroducePhotoService;
import com.songchi.seen.introduce.service.UserIntroducePhotoToPhotoPOService;

import lombok.AllArgsConstructor;

/**
 * IntroducePhotoServiceImpl
 *
 * @author chixuehui
 * @since 2024-01-07
 */
@Service
@AllArgsConstructor
public class IntroducePhotoServiceImpl implements IntroducePhotoService {

    /**
     * 用户介绍照片信息
     */
    private UserIntroducePhotoService userIntroducePhotoService;
    /**
     * 用户介绍照片信息对应关系
     */
    private UserIntroducePhotoToPhotoPOService userIntroducePhotoToPhotoPOService;

    /**
     * 根据用户ID获取介绍信息对应关系
     *
     * @param userIds 用户ID
     * @return 用户ID对应介绍信息对应关系
     */
    @Override
    public Map<Integer, Set<IntroduceTypeAndPhoto>> userIdToIntroduceTypeAndPhoto(Set<Integer> userIds) {
        Map<Integer, Set<IntroduceTypeAndPhotoInfo>> userIdToIntroduceTypeAndPhotoInfoMap = userIntroducePhotoService
                .userIdToIntroduceTypeAndPhoto(userIds);
        Set<Integer> ids = userIdToIntroduceTypeAndPhotoInfoMap.values()
                .stream().flatMap(Collection::stream)
                .map(IntroduceTypeAndPhotoInfo::id).collect(Collectors.toSet());
        Map<Integer, LocalDateTime> idToModifyTimeMap = userIntroducePhotoService.idToModifyTime(ids);
        // 过滤最新时间
        Map<Integer, Set<IntroduceTypeAndPhotoInfo>> userIdToIntroduceTypeAndPhotoInfoMapByFilter = userIdToIntroduceTypeAndPhotoInfoMap
                .entrySet().stream().parallel()
                .map(n -> {
                    Map<IntroduceTypeEnum, Map<Integer, Optional<IntroduceTypeAndPhotoInfo>>> collect = n.getValue()
                            .stream()
                            .collect(Collectors.groupingBy(IntroduceTypeAndPhotoInfo::introduceTypeEnum,
                                    Collectors.groupingBy(IntroduceTypeAndPhotoInfo::order,
                                            Collectors.maxBy((o1, o2) -> {
                                                Integer id1 = o1.id();
                                                LocalDateTime localDateTime1 = idToModifyTimeMap.get(id1);
                                                Integer id2 = o2.id();
                                                LocalDateTime localDateTime2 = idToModifyTimeMap.get(id2);
                                                return localDateTime1.compareTo(localDateTime2);
                                            }))));
                    Set<IntroduceTypeAndPhotoInfo> collect1 = collect.values().stream().map(Map::values)
                            .flatMap(Collection::stream)
                            .map(l -> l.orElse(null))
                            .filter(Objects::nonNull).collect(Collectors.toSet());
                    return Map.entry(n.getKey(), collect1);
                }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        Set<Integer> newIds = userIdToIntroduceTypeAndPhotoInfoMapByFilter.values().stream().parallel()
                .flatMap(Collection::stream).map(IntroduceTypeAndPhotoInfo::id).collect(Collectors.toSet());
        Map<Integer, Integer> idToPhotoIdMap = userIntroducePhotoToPhotoPOService.userIntroducePhotoIdToPhotoId(newIds);
        return userIdToIntroduceTypeAndPhotoInfoMapByFilter.entrySet()
                .stream()
                .parallel()
                .map(n -> {
                    Integer userId = n.getKey();
                    Set<IntroduceTypeAndPhoto> introduceTypeAndPhotos = n.getValue().stream()
                            .parallel()
                            .map(l -> {
                                Integer photoId = idToPhotoIdMap.get(l.id());
                                if (photoId == null) {
                                    // 该顺序号下没有照片
                                    return null;
                                }
                                return new IntroduceTypeAndPhoto(l.introduceTypeEnum(), l.order(), photoId);
                            }).filter(l -> l != null).collect(Collectors.toSet());
                    return Map.entry(userId, introduceTypeAndPhotos);
                }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

}
