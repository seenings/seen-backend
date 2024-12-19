package com.songchi.seen.introduce.service;

import com.songchi.seen.introduce.enumeration.IntroduceTypeEnum;
import com.songchi.seen.introduce.model.IntroduceTypeAndText;

import java.util.Map;
import java.util.Set;

/**
 * UserIntroduceService
 *
 * @author chixuehui
 * @since 2022-11-27
 */
public interface UserIntroduceService {
    Integer saveAndReturnId(Integer userId, IntroduceTypeEnum introduceTypeEnum, Integer textId);

    Integer updateAndReturnId(Integer userId, IntroduceTypeEnum introduceTypeEnum, Integer textId);

    Map<Integer, Set<IntroduceTypeAndText>> userIdToIntroduceTypeAndText(Set<Integer> userIds);
}
