package io.github.seenings.introduce.controller;

import io.github.seenings.introduce.enumeration.IntroduceTypeEnum;
import io.github.seenings.introduce.http.HttpUserIntroducePhotoService;
import io.github.seenings.introduce.model.IntroduceTypeAndPhoto;
import io.github.seenings.introduce.model.OrderAndPhotoId;
import io.github.seenings.introduce.service.IntroducePhotoService;
import io.github.seenings.introduce.service.UserIntroducePhotoService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * UserIntroducePhotoController
 *
 * @author chixuehui
 * @since 2022-11-27
 */
@RestController
@AllArgsConstructor
public class UserIntroducePhotoController implements HttpUserIntroducePhotoService {

    private UserIntroducePhotoService userIntroducePhotoService;

    private IntroducePhotoService introducePhotoService;

    /**
     * 根据用户ID获取介绍信息对应关系
     *
     * @param userIds 用户ID
     * @return 用户ID对应介绍信息对应关系
     */
    @Override
    public Map<Long, Set<IntroduceTypeAndPhoto>> userIdToIntroduceTypeAndPhoto(Set<Long> userIds) {
        return introducePhotoService.userIdToIntroduceTypeAndPhoto(userIds);
    }


    /**
     * 存入多个照片
     *
     * @param userId           用户ID
     * @param orderAndPhotoIds 介绍照片
     * @return 存入的介绍照片信息ID
     */
    @Override
    public Set<Integer> saveAndReturnId(List<OrderAndPhotoId> orderAndPhotoIds, Integer max, IntroduceTypeEnum introduceTypeEnum, Long userId) {
        return userIntroducePhotoService.saveAndReturnId(orderAndPhotoIds, max, introduceTypeEnum, userId);
    }
}
