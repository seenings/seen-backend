package io.github.seenings.introduce.http;

import static io.github.seenings.sys.constant.SeenConstant.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import io.github.seenings.introduce.enumeration.IntroduceTypeEnum;
import io.github.seenings.introduce.model.IntroduceTypeAndPhoto;
import io.github.seenings.introduce.model.OrderAndPhotoId;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

/**
 * HttpUserIntroducePhotoService
 *
 * @author chixuehui
 * @since 2022-11-27
 */
@HttpExchange(  FEIGN_VERSION
        + "introduce/user-introduce-photo" )
public interface HttpUserIntroducePhotoService {

    /**
     * 根据用户ID获取介绍照片信息
     *
     * @param userIds 用户ID
     * @return 用户ID对应介绍照片信息
     */
    @PostExchange("user-id-to-introduce-type-and-photo")
    Map<Long, Set<IntroduceTypeAndPhoto>> userIdToIntroduceTypeAndPhoto(@RequestBody Set<Long> userIds);

    /**
     * 存入多个照片
     *
     * @param userId           用户ID
     * @param orderAndPhotoIds 介绍照片
     * @return 存入的介绍照片信息ID
     */
    @PostExchange("save-and-return-id")
    Set<Integer> saveAndReturnId(@RequestBody List<OrderAndPhotoId> orderAndPhotoIds,
            @RequestParam Integer max, @RequestParam IntroduceTypeEnum introduceTypeEnum,
            @RequestParam("userId") Long userId);
}
