package io.github.seenings.introduce.service;

import io.github.seenings.introduce.enumeration.IntroduceTypeEnum;
import io.github.seenings.introduce.model.IntroduceTypeAndText;

import java.util.Map;
import java.util.Set;

/**
 * UserIntroduceService
 *
 * @author chixuehui
 * @since 2022-11-27
 */
public interface UserIntroduceService {
    Integer saveAndReturnId(Long userId, IntroduceTypeEnum introduceTypeEnum, Integer textId);

    Integer updateAndReturnId(Long userId, IntroduceTypeEnum introduceTypeEnum, Integer textId);

    Map<Long, Set<IntroduceTypeAndText>> userIdToIntroduceTypeAndText(Set<Long> userIds);
}
