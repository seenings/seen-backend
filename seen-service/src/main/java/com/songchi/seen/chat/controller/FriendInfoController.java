package com.songchi.seen.chat.controller;

import com.songchi.seen.apply.http.HttpUserApplyService;
import com.songchi.seen.chat.enumeration.ApplyStatus;
import com.songchi.seen.chat.model.RecInfo;
import com.songchi.seen.chat.model.SendInfo;
import com.songchi.seen.chat.service.ApplyService;
import com.songchi.seen.common.model.R;
import com.songchi.seen.common.util.ResUtils;
import com.songchi.seen.info.http.HttpUserAliasNameService;
import com.songchi.seen.info.http.HttpUserMainPhotoService;
import com.songchi.seen.photo.http.HttpPhotoService;
import com.songchi.seen.sys.constant.PublicConstant;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * FriendInfoController
 *
 * @author chixuehui
 * @since 2023-03-05
 */
@RestController
@RequestMapping(PublicConstant.REST + "chat/friend-info")
public class FriendInfoController {

    @Resource
    private HttpUserApplyService httpUserApplyService;
    @Resource
    private HttpUserAliasNameService httpUserAliasNameService;

    @Resource
    private HttpUserMainPhotoService httpUserMainPhotoService;
    @Resource
    private HttpPhotoService httpPhotoService;

    @Resource
    private ApplyService applyService;

    @PostMapping("self-user-id-to-send-info")
    public R<List<SendInfo>> selfUserIdToSendInfo(@SessionAttribute Integer userId) {

        List<Integer> applyIds = httpUserApplyService.applyUserIdToApplyIdByPage(userId, 1, 10);

        Set<Integer> applyIdSet = new HashSet<>(applyIds);
        Map<Integer, Integer> applyIdToAppliedUserIdMap = httpUserApplyService.applyIdToAppliedUserId(applyIdSet);
        Set<Integer> userIds = new HashSet<>(applyIdToAppliedUserIdMap.values());
        Map<Integer, String> userIdToAliasNameMap = httpUserAliasNameService.userIdToAliasName(userIds);
        Map<Integer, Integer> userIdToPhotoIdMap = httpUserMainPhotoService
                .userIdPhotoId(userIds);
        Map<Integer, String> photoIdToLongPhotoUrlMap = httpPhotoService
                .photoIdToLongPhotoUrl(new HashSet<>(userIdToPhotoIdMap.values()));

        Map<Integer, LocalDateTime> applyIdToApplyTimeMap = httpUserApplyService.applyIdToApplyTime(applyIdSet);
        Map<Integer, ApplyStatus> applyIdToApplyStatusMap = applyService.applyIdToApplyStatus(applyIdSet);
        List<SendInfo> result = applyIds.stream()
                .map(applyId -> {
                    Integer recUserId = applyIdToAppliedUserIdMap.get(applyId);
                    ApplyStatus applyStatus = applyIdToApplyStatusMap.get(applyId);
                    String name = userIdToAliasNameMap.get(recUserId);
                    Integer photoId = userIdToPhotoIdMap.get(recUserId);
                    String mainPhotoUrl = photoIdToLongPhotoUrlMap.get(photoId);
                    LocalDateTime applyTime = applyIdToApplyTimeMap.get(applyId);
                    return new SendInfo(applyId, mainPhotoUrl, name, applyStatus.getIndex(), recUserId,
                            applyTime
                    );
                }).collect(Collectors.toList());
        return ResUtils.ok(result);
    }

    @PostMapping("self-user-id-to-rec-info")
    public R<List<RecInfo>> selfUserIdToRecInfo(@SessionAttribute Integer userId) {
        List<Integer> applyIds = httpUserApplyService.appliedUserIdToApplyIdByPage(userId, 1, 10);

        Set<Integer> applyIdSet = new HashSet<>(applyIds);
        Map<Integer, Integer> applyIdToUserIdMap = httpUserApplyService.applyIdToApplyUserId(applyIdSet);
        Set<Integer> userIds = new HashSet<>(applyIdToUserIdMap.values());
        Map<Integer, String> userIdToAliasNameMap = httpUserAliasNameService.userIdToAliasName(userIds);
        Map<Integer, Integer> userIdToPhotoIdMap = httpUserMainPhotoService
                .userIdPhotoId(userIds);
        Map<Integer, String> photoIdToLongPhotoUrlMap = httpPhotoService
                .photoIdToLongPhotoUrl(new HashSet<>(userIdToPhotoIdMap.values()));
        Map<Integer, ApplyStatus> applyIdToApplyStatusMap = applyService.applyIdToApplyStatus(applyIdSet);
        List<RecInfo> result = applyIds.stream()
                .map(applyId -> {
                    Integer recUserId = applyIdToUserIdMap.get(applyId);
                    ApplyStatus applyStatus = applyIdToApplyStatusMap.get(applyId);
                    String name = userIdToAliasNameMap.get(recUserId);
                    Integer photoId = userIdToPhotoIdMap.get(recUserId);
                    String mainPhotoUrl = photoIdToLongPhotoUrlMap.get(photoId);
                    return new RecInfo(applyId, mainPhotoUrl, name, applyStatus.getIndex(), recUserId);
                }).collect(Collectors.toList());
        return ResUtils.ok(result);
    }
}
