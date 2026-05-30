package io.github.seenings.thumb.controller;

import cn.hutool.core.lang.Pair;
import io.github.seenings.thumb.http.HttpFocusUserService;
import io.github.seenings.thumb.service.FocusUserPOService;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class FocusUserController implements HttpFocusUserService {
    private FocusUserPOService focusUserPOService;

    /**
     * 根据被关注者获取是否关注
     * @param focusedUserIds 被关注者
     * @param focusUserId   关注者
     * @return  被关注者对应是否关注
     */
    @Override
    public Map<Long, Boolean> focusedUserIdToTrue(
            @RequestBody Set<Long> focusedUserIds, @RequestParam("focusUserId") Long focusUserId) {
        Map<Long, Boolean> focusedUserIdToTrueMap =
                focusUserPOService.focusedUserIdToTrue(focusedUserIds, focusUserId);
        return focusedUserIds.stream()
                .parallel()
                .map(n -> Pair.of(n, focusedUserIdToTrueMap.getOrDefault(n, false)))
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue, (_, o2) -> o2));
    }

    @Override
    public boolean set(
            @RequestParam("focusedUserId") Long focusedUserId, @RequestParam("focusUserId") Long focusUserId) {
        return focusUserPOService.set(focusedUserId, focusUserId);
    }
}
