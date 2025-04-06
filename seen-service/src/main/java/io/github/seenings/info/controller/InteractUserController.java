package io.github.seenings.info.controller;

import io.github.seenings.common.model.R;
import io.github.seenings.common.util.ResUtils;
import io.github.seenings.sys.constant.PublicConstant;
import io.github.seenings.thumb.http.HttpFocusUserService;
import io.github.seenings.thumb.http.HttpThumbUserService;
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
    public R<Map<Long, Boolean>> focusUser(@RequestBody Set<Long> destUserIds, @SessionAttribute Long userId) {
        Map<Long, Boolean> result = destUserIds.stream()
                .parallel()
                .map(n -> Pair.of(n, httpFocusUserService.set(n, userId)))
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue, (o1, o2) -> o2));
        return ResUtils.ok(result);
    }

    @PostMapping("thumb-user")
    public R<Map<Long, Boolean>> thumbUser(@RequestBody Set<Long> destUserIds, @SessionAttribute Long userId) {
        Map<Long, Boolean> result = destUserIds.stream()
                .parallel()
                .map(n -> Pair.of(n, httpThumbUserService.set(n, userId)))
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue, (o1, o2) -> o2));
        return ResUtils.ok(result);
    }
}
