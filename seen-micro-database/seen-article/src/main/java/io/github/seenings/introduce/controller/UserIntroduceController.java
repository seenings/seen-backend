package io.github.seenings.introduce.controller;

import io.github.seenings.introduce.enumeration.IntroduceTypeEnum;
import io.github.seenings.introduce.http.HttpUserIntroduceService;
import io.github.seenings.introduce.model.IntroduceTypeAndText;
import io.github.seenings.introduce.service.UserIntroduceService;
import io.github.seenings.sys.constant.SeenConstant;
import jakarta.annotation.Resource;
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
@RequestMapping(SeenConstant.FEIGN_VERSION + "introduce/user-introduce")
public class UserIntroduceController implements HttpUserIntroduceService {

    @Resource
    private UserIntroduceService userIntroduceService;

    /**
     * 根据用户ID获取介绍文本
     *
     * @param userIds 用户ID
     * @return 用户ID对应介绍文本
     */
    @Override
    @PostMapping("user-id-to-introduce-type-and-text")
    public Map<Long, Set<IntroduceTypeAndText>> userIdToIntroduceTypeAndText(@RequestBody Set<Long> userIds) {
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
    @PostMapping("save-and-return-id")
    public Integer saveAndReturnId(
            @RequestParam("userId") Long userId,
            @RequestParam("introduceTypeEnum") IntroduceTypeEnum introduceTypeEnum,
            @RequestParam("textId") Integer textId) {
        return userIntroduceService.saveAndReturnId(userId, introduceTypeEnum, textId);
    }
}
