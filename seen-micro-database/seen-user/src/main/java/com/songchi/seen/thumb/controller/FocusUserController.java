package com.songchi.seen.thumb.controller;

import cn.hutool.core.lang.Pair;
import com.songchi.seen.sys.constant.SeenConstant;
import com.songchi.seen.thumb.http.HttpFocusUserService;
import com.songchi.seen.thumb.service.FocusUserPOService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * FocusUserController
 *
 * @author chixuehui
 * @since 2023-01-23
 */
@RestController
@RequestMapping(SeenConstant.FEIGN_VERSION + "thumb/focus")
public class FocusUserController implements HttpFocusUserService {
    @Resource
    private FocusUserPOService focusUserPOService;

    /**
     * 根据被关注者获取是否关注
     * @param focusedUserIds 被关注者
     * @param focusUserId   关注者
     * @return  被关注者对应是否关注
     */
    @Override
    @PostMapping("focused-user-id-to-true")
    public Map<Integer, Boolean> focusedUserIdToTrue(
            @RequestBody Set<Integer> focusedUserIds, @RequestParam("focusUserId") Integer focusUserId) {
        Map<Integer, Boolean> focusedUserIdToTrueMap =
                focusUserPOService.focusedUserIdToTrue(focusedUserIds, focusUserId);
        return focusedUserIds.stream()
                .parallel()
                .map(n -> Pair.of(n, focusedUserIdToTrueMap.getOrDefault(n, false)))
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue, (o1, o2) -> o2));
    }

    @Override
    @PostMapping("set")
    public boolean set(
            @RequestParam("focusedUserId") Integer focusedUserId, @RequestParam("focusUserId") Integer focusUserId) {
        return focusUserPOService.set(focusedUserId, focusUserId);
    }
}
