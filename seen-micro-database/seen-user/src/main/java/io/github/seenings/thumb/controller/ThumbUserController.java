package io.github.seenings.thumb.controller;

import cn.hutool.core.lang.Pair;
import io.github.seenings.sys.constant.SeenConstant;
import io.github.seenings.thumb.http.HttpThumbUserService;
import io.github.seenings.thumb.service.ThumbUserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * ThumbUserController
 *
 * @author chixuehui
 * @since 2023-01-23
 */
@RestController
@RequestMapping(SeenConstant.FEIGN_VERSION + "thumb/thumb")
public class ThumbUserController implements HttpThumbUserService {
    @Resource
    private ThumbUserService thumbUserService;

    /**
     * 根据被点赞者获取是否关注
     * @param thumbedUserIds 被点赞者
     * @param thumbUserId   点赞者
     * @return  被点赞者对应是否点赞
     */
    @Override
    @PostMapping("thumbed-user-id-to-true")
    public Map<Long, Boolean> thumbedUserIdToTrue(
            @RequestBody Set<Long> thumbedUserIds, @RequestParam("thumbUserId") Long thumbUserId) {
        Map<Long, Boolean> thumbedUserIdToTrueMap =
                thumbUserService.thumbedUserIdToTrue(thumbedUserIds, thumbUserId);
        return thumbedUserIds.stream()
                .parallel()
                .map(n -> Pair.of(n, thumbedUserIdToTrueMap.getOrDefault(n, false)))
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue, (o1, o2) -> o2));
    }

    @Override
    @PostMapping("set")
    public boolean set(
            @RequestParam("thumbedUserId") Long thumbedUserId, @RequestParam("thumbUserId") Long thumbUserId) {
        return thumbUserService.set(thumbedUserId, thumbUserId);
    }
}
