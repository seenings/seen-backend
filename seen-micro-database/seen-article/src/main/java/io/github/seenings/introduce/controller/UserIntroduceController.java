package io.github.seenings.introduce.controller;

import io.github.seenings.introduce.enumeration.IntroduceTypeEnum;
import io.github.seenings.introduce.http.HttpUserIntroduceService;
import io.github.seenings.introduce.model.IntroduceTypeAndText;
import io.github.seenings.introduce.service.UserIntroduceService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

/**
 * UserIntroduceController
 *
 * @author chixuehui
 * @since 2022-11-27
 */
@RestController
@AllArgsConstructor
public class UserIntroduceController implements HttpUserIntroduceService {

    private UserIntroduceService userIntroduceService;

    /**
     * 根据用户ID获取介绍文本
     *
     * @param userIds 用户ID
     * @return 用户ID对应介绍文本
     */
    @Override
    public Map<Long, Set<IntroduceTypeAndText>> userIdToIntroduceTypeAndText(Set<Long> userIds) {
        return userIntroduceService.userIdToIntroduceTypeAndText(userIds);
    }


    /**
     * 保存用户的介绍
     *
     * @param userId            用户ID
     * @param introduceTypeEnum 介绍类型
     * @param textId            文本ID
     * @return 用户介绍ID
     */
    @Override
    public Integer saveAndReturnId(Long userId, IntroduceTypeEnum introduceTypeEnum, Integer textId) {
        return userIntroduceService.saveAndReturnId(userId, introduceTypeEnum, textId);
    }
}
