package com.songchi.seen.thumb.http;

import com.songchi.seen.sys.constant.SeenConstant;
import com.songchi.seen.sys.constant.ServiceNameConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.Set;

/**
 * HttpFocusUserService
 *
 * @author chixuehui
 * @since 2023-01-23
 */
@FeignClient(
        name = ServiceNameConstant.SERVICE_SEEN_USER,
        path = SeenConstant.FEIGN_VERSION + "thumb/focus",
        contextId = "HttpFocusUserService")
public interface HttpFocusUserService {
    /**
     * 根据被关注者获取是否关注
     * @param focusedUserIds 被关注者
     * @param focusUserId   关注者
     * @return  被关注者对应是否关注
     */
    @PostMapping("focused-user-id-to-true")
    Map<Long, Boolean> focusedUserIdToTrue(
            @RequestBody Set<Long> focusedUserIds, @RequestParam("focusUserId") Long focusUserId);

    @PostMapping("set")
    boolean set(@RequestParam("focusedUserId") Long focusedUserId, @RequestParam("focusUserId") Long focusUserId);
}
