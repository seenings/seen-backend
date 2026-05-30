package io.github.seenings.info.http;

import io.github.seenings.sys.constant.SeenConstant;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.Map;
import java.util.Set;

/**
 * HttpUserWorkPositionService
 *
 * @author chixuehui
 * @since 2022-10-16
 */

@HttpExchange(
        value = SeenConstant.FEIGN_VERSION + "user/user-work-position")
public interface HttpUserWorkPositionService {
    @PostExchange("user-id-to-position")
    Map<Long, Integer> userIdToPosition(@RequestBody Set<Long> userIds);

    @PostExchange("set")
    boolean set(@RequestParam("userId") Long userId,@RequestParam("position") Integer position);
}
