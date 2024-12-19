package com.songchi.seen.info.controller;

import cn.hutool.core.util.StrUtil;
import com.songchi.seen.common.model.R;
import com.songchi.seen.common.util.ResUtils;
import com.songchi.seen.info.model.*;
import com.songchi.seen.info.service.InfoService;
import com.songchi.seen.info.service.UserInfoService;
import com.songchi.seen.introduce.enumeration.IntroduceTypeEnum;
import com.songchi.seen.introduce.http.HttpUserIntroducePhotoService;
import com.songchi.seen.introduce.http.HttpUserIntroduceService;
import com.songchi.seen.introduce.model.OrderAndPhotoId;
import com.songchi.seen.introduce.util.IntroduceEnumUtils;
import com.songchi.seen.sys.constant.PublicConstant;
import com.songchi.seen.text.http.HttpTagService;
import com.songchi.seen.text.http.HttpTextService;
import com.songchi.seen.thumb.http.HttpFocusUserService;
import com.songchi.seen.thumb.http.HttpThumbUserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 用户信息 前端控制器
 * </p>
 *
 * @author chixh
 * @since 2021-05-08
 */
@Slf4j
@RestController
@RequestMapping(PublicConstant.REST + "user/info")
public class InfoController {

    @Resource
    private HttpThumbUserService httpThumbUserService;
    @Resource
    private HttpFocusUserService httpFocusUserService;
    @Resource
    private HttpTextService httpTextService;

    @Resource
    private HttpUserIntroduceService httpUserIntroduceService;

    @Resource
    private HttpUserIntroducePhotoService httpUserIntroducePhotoService;

    @Resource
    private InfoService infoService;

    @Resource
    private UserInfoService userInfoService;

    @PostMapping("user-id-to-user-main-info")
    public R<Map<Integer, UserMainInfo>> userIdToUserMainInfo(@RequestBody Set<Integer> userIds) {
        Map<Integer, UserMainInfo> map = userInfoService.userIdToUserMainInfo(userIds);
        return ResUtils.ok(map);
    }

    @Resource
    private HttpTagService httpTagService;

    @PostMapping("user-id-to-tag-name")
    public R<Map<Integer, Set<String>>> userIdToTagName(@RequestBody Set<Integer> userIds) {
        Map<Integer, Set<String>> map = httpTagService.userIdToTagName(userIds);
        return ResUtils.ok(map);
    }

    @GetMapping("self-user-info")
    public R<UserInfo> selfUserInfo(@SessionAttribute Integer userId) {
        Set<Integer> userIds = Collections.singleton(userId);
        Map<Integer, UserInfo> userIdToUserInfoMap = infoService.userIdToUserInfo(userIds);
        if (userIdToUserInfoMap == null || userIdToUserInfoMap.get(userId) == null) {
            String msg = String.format("用户不存在{}，用户ID：%s。", userId);
            log.error("{}", msg);
            return ResUtils.error(msg);
        }
        return ResUtils.ok(userIdToUserInfoMap.get(userId));
    }

    @PostMapping("user-id-to-user-introduce-info")
    public R<Map<Integer, UserIntroduceInfo>> userIdToUserIntroduceInfo(@RequestBody Set<Integer> userIds) {

        Map<Integer, UserIntroduceInfo> result = infoService.userIdToUserIntroduceInfo(userIds);
        return ResUtils.ok(result);
    }

    @PostMapping("user-id-to-person-introduce")
    public R<Map<Integer, List<PersonIntroduce>>> userIdToPersonIntroduce(@RequestBody Set<Integer> userIds) {

        Map<Integer, List<PersonIntroduce>> userIdToPersonIntroduce = infoService.userIdToPersonIntroduce(userIds);
        return ResUtils.ok(userIdToPersonIntroduce);
    }

    @PostMapping("introduce-type-index-to-person-introduce")
    public R<Map<Integer, PersonIntroduce>> introduceTypeIndexToPersonIntroduce(@RequestBody Set<Integer> introduceTypeIndexs, @SessionAttribute Integer userId) {
        Map<Integer, PersonIntroduce> result = infoService.introduceTypeIndexToPersonIntroduce(introduceTypeIndexs, userId);
        return ResUtils.ok(result);
    }

    @PostMapping("save-person-introduce")
    public R<Boolean> savePersonIntroduce(@RequestBody PersonIntroduceSave personIntroduceSave, @SessionAttribute Integer userId) {
        // 校验
        String introduceContent = personIntroduceSave.introduceContent();
        // 去除无字符时的空白字段
        introduceContent = StrUtil.blankToDefault(introduceContent, "");
        if (introduceContent.length() > 800) {
            String msg = String.format("长度过长，长度：%s。", introduceContent.length());
            log.error("{}", msg);
            return ResUtils.error(msg);
        }
        List<OrderAndPhotoId> orderAndPhotoIds = personIntroduceSave.orderAndPhotoIds();
        if (orderAndPhotoIds == null) {
            String msg = "照片ID不能为空。";
            log.error("{}", msg);
            return ResUtils.error(msg);
        }
        IntroduceTypeEnum introduceTypeEnum = IntroduceEnumUtils.indexToIntroduceTypeEnum(personIntroduceSave.introduceTypeIndex());
        if (introduceTypeEnum == null) {
            String msg = "用户介绍类型不能为空。";
            log.error("{}", msg);
            return ResUtils.error(msg);
        }
        Integer textId = httpTextService.saveAndReturnId(introduceContent);
        httpUserIntroduceService.saveAndReturnId(userId, introduceTypeEnum, textId);
        httpUserIntroducePhotoService.saveAndReturnId(orderAndPhotoIds, personIntroduceSave.max(), introduceTypeEnum, userId);
        return ResUtils.ok(true);
    }

    /**
     * 根据用户ID获取用户信息
     *
     * @param userIds 用户ID
     * @return 用户ID对应用户信息
     */
    @PostMapping("user-id-to-user-info")
    public R<Map<Integer, UserInfo>> userIdToUserInfo(@RequestBody Set<Integer> userIds) {
        Map<Integer, UserInfo> userIdToUserInfoMap = infoService.userIdToUserInfo(userIds);
        return ResUtils.ok(userIdToUserInfoMap);
    }

    @PostMapping("dest-user-id-to-thumb")
    public R<Map<Integer, Boolean>> destUserIdToThumb(@RequestBody Set<Integer> destUserIds, @SessionAttribute Integer userId) {
        Map<Integer, Boolean> result = httpThumbUserService.thumbedUserIdToTrue(destUserIds, userId);
        return ResUtils.ok(result);
    }


    @PostMapping("dest-user-id-to-focus")
    public R<Map<Integer, Boolean>> destUserIdToFocus(@RequestBody Set<Integer> destUserIds, @SessionAttribute Integer userId) {
        Map<Integer, Boolean> result = httpFocusUserService.focusedUserIdToTrue(destUserIds, userId);
        return ResUtils.ok(result);
    }

}
