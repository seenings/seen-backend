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

    Map<Integer, UserInfo> userIdToUserInfo(Set<Integer> userIds);

    Map<Integer, String> userIdToUserName(Set<Integer> userIds);

    Map<Integer, Integer> userIdToProfilePhotoId(Set<Integer> userIds);

    Map<Integer, Sex> userIdToSex(Set<Integer> userIds);

    Map<Integer, LocalDateTime> newUserId(int top);

    Map<Integer, UserIntroduceInfo> userIdToUserIntroduceInfo(@RequestBody Set<Integer> userIds);

    Map<Integer, BasicInfo> userIdToBasicInfo(Set<Integer> userIds);

    Map<Integer, List<PersonIntroduce>> userIdToPersonIntroduce(Set<Integer> userIds);

    Map<Integer, PersonIntroduce> introduceTypeIndexToPersonIntroduce(Set<Integer> introduceTypeIndexes, Integer userId);

    Map<Integer, UserChatInfo> userIdToUserChatInfo(Set<Integer> userIds, Integer selfUserId);
}
