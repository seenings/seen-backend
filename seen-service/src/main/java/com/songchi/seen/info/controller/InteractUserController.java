package com.songchi.seen.info.controller;

import com.songchi.seen.common.model.R;
import com.songchi.seen.common.util.ResUtils;
import com.songchi.seen.sys.constant.PublicConstant;
import com.songchi.seen.thumb.http.HttpFocusUserService;
import com.songchi.seen.thumb.http.HttpThumbUserService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 点赞用户 前端控制器
 * </p>
 *
 * @author chixh
 * @since 2021-05-08
 */
@RestController
@RequestMapping(PublicConstant.REST + "user/interact-user")
public class InteractUserController {

    @Resource
    private HttpThumbUserService httpThumbUserService;

    @Resource
    private HttpFocusUserService httpFocusUserService;

    @PostMapping("focus-user")
    public R<Map<Integer, Boolean>> focusUser(@RequestBody Set<Integer> destUserIds, @SessionAttribute Integer userId) {
        Map<Integer, Boolean> result = destUserIds.stream()
                .parallel()
                .map(n -> Pair.of(n, httpFocusUserService.set(n, userId)))
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue, (o1, o2) -> o2));
        return ResUtils.ok(result);
    }

    @PostMapping("thumb-user")
    public R<Map<Integer, Boolean>> thumbUser(@RequestBody Set<Integer> destUserIds, @SessionAttribute Integer userId) {
        Map<Integer, Boolean> result = destUserIds.stream()
                .parallel()
                .map(n -> Pair.of(n, httpThumbUserService.set(n, userId)))
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue, (o1, o2) -> o2));
        return ResUtils.ok(result);
    }
}
