package com.songchi.seen.introduce.controller;

import com.songchi.seen.introduce.enumeration.IntroduceTypeEnum;
import com.songchi.seen.introduce.http.HttpUserIntroducePhotoService;
import com.songchi.seen.introduce.model.IntroduceTypeAndPhoto;
import com.songchi.seen.introduce.model.OrderAndPhotoId;
import com.songchi.seen.introduce.service.IntroducePhotoService;
import com.songchi.seen.introduce.service.UserIntroducePhotoService;
import com.songchi.seen.sys.constant.SeenConstant;
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
@RequestMapping(SeenConstant.FEIGN_VERSION + "introduce/user-introduce-photo")
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
    public Map<Long, Set<IntroduceTypeAndPhoto>> userIdToIntroduceTypeAndPhoto(@RequestBody Set<Long> userIds) {
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
    @PostMapping("save-and-return-id")
    public Set<Integer> saveAndReturnId(@RequestBody List<OrderAndPhotoId> orderAndPhotoIds, @RequestParam Integer max, @RequestParam IntroduceTypeEnum introduceTypeEnum, @RequestParam("userId") Long userId) {
        return userIntroducePhotoService.saveAndReturnId(orderAndPhotoIds, max, introduceTypeEnum, userId);
    }
}
