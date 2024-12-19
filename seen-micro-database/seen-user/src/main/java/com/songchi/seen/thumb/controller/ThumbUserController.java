package com.songchi.seen.thumb.controller;

import cn.hutool.core.lang.Pair;
import com.songchi.seen.sys.constant.SeenConstant;
import com.songchi.seen.thumb.http.HttpThumbUserService;
import com.songchi.seen.thumb.service.ThumbUserService;
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
    public Map<Integer, Boolean> thumbedUserIdToTrue(
            @RequestBody Set<Integer> thumbedUserIds, @RequestParam("thumbUserId") Integer thumbUserId) {
        Map<Integer, Boolean> thumbedUserIdToTrueMap =
                thumbUserService.thumbedUserIdToTrue(thumbedUserIds, thumbUserId);
        return thumbedUserIds.stream()
                .parallel()
                .map(n -> Pair.of(n, thumbedUserIdToTrueMap.getOrDefault(n, false)))
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue, (o1, o2) -> o2));
    }

    @Override
    @PostMapping("set")
    public boolean set(
            @RequestParam("thumbedUserId") Integer thumbedUserId, @RequestParam("thumbUserId") Integer thumbUserId) {
        return thumbUserService.set(thumbedUserId, thumbUserId);
    }
}
