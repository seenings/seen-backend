package com.songchi.seen.info.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.web.bind.annotation.RequestBody;

import com.songchi.seen.chat.model.UserChatInfo;
import com.songchi.seen.info.enumeration.Sex;
import com.songchi.seen.info.model.BasicInfo;
import com.songchi.seen.info.model.PersonIntroduce;
import com.songchi.seen.info.model.UserInfo;
import com.songchi.seen.info.model.UserIntroduceInfo;

/**
 * <p>
 * 用户信息 服务类
 * </p>
 *
 * @author chixh
 * @since 2021-05-08
 */
public interface InfoService {

    Map<Long, UserInfo> userIdToUserInfo(Set<Long> userIds);

    Map<Long, String> userIdToUserName(Set<Long> userIds);

    Map<Long, Integer> userIdToProfilePhotoId(Set<Long> userIds);

    Map<Long, Sex> userIdToSex(Set<Long> userIds);

    Map<Long, LocalDateTime> newUserId(int top);

    Map<Long, UserIntroduceInfo> userIdToUserIntroduceInfo(@RequestBody Set<Long> userIds);

    Map<Long, BasicInfo> userIdToBasicInfo(Set<Long> userIds);

    Map<Long, List<PersonIntroduce>> userIdToPersonIntroduce(Set<Long> userIds);

    Map<Integer, PersonIntroduce> introduceTypeIndexToPersonIntroduce(Set<Integer> introduceTypeIndexes, Long userId);

    Map<Long, UserChatInfo> userIdToUserChatInfo(Set<Long> userIds, Long selfUserId);
}
