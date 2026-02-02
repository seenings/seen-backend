package io.github.seenings.introduce.http;

import io.github.seenings.introduce.enumeration.IntroduceTypeEnum;
import io.github.seenings.introduce.model.IntroduceTypeAndText;
import io.github.seenings.sys.constant.ServiceNameConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.Map;
import java.util.Set;

import static io.github.seenings.sys.constant.SeenConstant.FEIGN_VERSION;

/**
 * HttpUserIntroduceService
 *
 * @author chixuehui
 * @since 2022-11-27
 */
@HttpExchange( FEIGN_VERSION + "introduce/user-introduce" )
public interface HttpUserIntroduceService {
    /**
     * 根据用户ID获取介绍文本
     *
     * @param userIds 用户ID
     * @return 用户ID对应介绍文本
     */
    @PostExchange("user-id-to-introduce-type-and-text")
    Map<Long, Set<IntroduceTypeAndText>> userIdToIntroduceTypeAndText(@RequestBody Set<Long> userIds);


    /**
     * 保存用户的介绍
     *
     * @param userId            用户ID
     * @param introduceTypeEnum 介绍类型
     * @param textId            文本ID
     * @return 用户介绍ID
     */
    @PostExchange("save-and-return-id")
    Integer saveAndReturnId(@RequestParam("userId") Long userId, @RequestParam("introduceTypeEnum") IntroduceTypeEnum introduceTypeEnum, @RequestParam("textId") Integer textId);
}
