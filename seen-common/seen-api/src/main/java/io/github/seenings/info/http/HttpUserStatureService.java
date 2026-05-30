package io.github.seenings.info.http;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.Map;
import java.util.Set;

import static io.github.seenings.sys.constant.SeenConstant.FEIGN_VERSION;

/**
 * HttpUserStatureService
 *
 * @author chixuehui
 * @since 2022-10-16
 */
@HttpExchange(
        value = FEIGN_VERSION + "user/user-stature")
public interface HttpUserStatureService {

    @PostExchange("user-id-to-stature-cm")
    Map<Long, Integer> userIdToStatureCm(@RequestBody Set<Long> userIds);

    @PostExchange("set")
    boolean set(@RequestParam("userId") Long userId, @RequestParam("statureCm") Integer statureCm);
}
