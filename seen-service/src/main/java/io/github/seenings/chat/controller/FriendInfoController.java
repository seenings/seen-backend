package io.github.seenings.chat.controller;

import io.github.seenings.apply.http.HttpUserApplyService;
import io.github.seenings.chat.enumeration.ApplyStatus;
import io.github.seenings.chat.model.RecInfo;
import io.github.seenings.chat.model.SendInfo;
import io.github.seenings.chat.service.ApplyService;
import io.github.seenings.common.model.R;
import io.github.seenings.common.util.ResUtils;
import io.github.seenings.info.http.HttpUserAliasNameService;
import io.github.seenings.info.http.HttpUserMainPhotoService;
import io.github.seenings.photo.http.HttpPhotoService;
import io.github.seenings.sys.constant.PublicConstant;
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
    public R<List<SendInfo>> selfUserIdToSendInfo(@SessionAttribute Long userId) {

        List<Integer> applyIds = httpUserApplyService.applyUserIdToApplyIdByPage(userId, 1, 10);

        Set<Integer> applyIdSet = new HashSet<>(applyIds);
        Map<Integer, Long> applyIdToAppliedUserIdMap = httpUserApplyService.applyIdToAppliedUserId(applyIdSet);
        Set<Long> userIds = new HashSet<>(applyIdToAppliedUserIdMap.values());
        Map<Long, String> userIdToAliasNameMap = httpUserAliasNameService.userIdToAliasName(userIds);
        Map<Long, Integer> userIdToPhotoIdMap = httpUserMainPhotoService
                .userIdPhotoId(userIds);

        Map<Integer, LocalDateTime> applyIdToApplyTimeMap = httpUserApplyService.applyIdToApplyTime(applyIdSet);
        Map<Integer, ApplyStatus> applyIdToApplyStatusMap = applyService.applyIdToApplyStatus(applyIdSet);
        List<SendInfo> result = applyIds.stream()
                .map(applyId -> {
                    Long recUserId = applyIdToAppliedUserIdMap.get(applyId);
                    ApplyStatus applyStatus = applyIdToApplyStatusMap.get(applyId);
                    String name = userIdToAliasNameMap.get(recUserId);
                    Integer photoId = userIdToPhotoIdMap.get(recUserId);
                    LocalDateTime applyTime = applyIdToApplyTimeMap.get(applyId);
                    return new SendInfo(photoId, applyId, name, applyStatus.getIndex(), recUserId,
                            applyTime
                    );
                }).collect(Collectors.toList());
        return ResUtils.ok(result);
    }

    @PostMapping("self-user-id-to-rec-info")
    public R<List<RecInfo>> selfUserIdToRecInfo(@SessionAttribute Long userId) {
        List<Integer> applyIds = httpUserApplyService.appliedUserIdToApplyIdByPage(userId, 1, 10);

        Set<Integer> applyIdSet = new HashSet<>(applyIds);
        Map<Integer, Long> applyIdToUserIdMap = httpUserApplyService.applyIdToApplyUserId(applyIdSet);
        Set<Long> userIds = new HashSet<>(applyIdToUserIdMap.values());
        Map<Long, String> userIdToAliasNameMap = httpUserAliasNameService.userIdToAliasName(userIds);
        Map<Long, Integer> userIdToPhotoIdMap = httpUserMainPhotoService
                .userIdPhotoId(userIds);
        Map<Integer, ApplyStatus> applyIdToApplyStatusMap = applyService.applyIdToApplyStatus(applyIdSet);
        List<RecInfo> result = applyIds.stream()
                .map(applyId -> {
                    Long recUserId = applyIdToUserIdMap.get(applyId);
                    ApplyStatus applyStatus = applyIdToApplyStatusMap.get(applyId);
                    String name = userIdToAliasNameMap.get(recUserId);
                    Integer photoId = userIdToPhotoIdMap.get(recUserId);
                    return new RecInfo(photoId, applyId, name, applyStatus.getIndex(), recUserId);
                }).collect(Collectors.toList());
        return ResUtils.ok(result);
    }
}
