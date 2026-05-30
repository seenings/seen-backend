package io.github.seenings.thumb.http;

import io.github.seenings.sys.constant.SeenConstant;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.Map;
import java.util.Set;

/**
 * HttpFocusUserService
 *
 * @author chixuehui
 * @since 2023-01-23
 */
@HttpExchange(
        value = SeenConstant.FEIGN_VERSION + "thumb/focus")
public interface HttpFocusUserService {
    /**
     * 根据被关注者获取是否关注
     * @param focusedUserIds 被关注者
     * @param focusUserId   关注者
     * @return  被关注者对应是否关注
     */
    @PostExchange("focused-user-id-to-true")
    Map<Long, Boolean> focusedUserIdToTrue(
            @RequestBody Set<Long> focusedUserIds, @RequestParam("focusUserId") Long focusUserId);

    @PostExchange("set")
    boolean set(@RequestParam("focusedUserId") Long focusedUserId, @RequestParam("focusUserId") Long focusUserId);
}
