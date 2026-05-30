package io.github.seenings.info.http;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.Map;
import java.util.Set;

import static io.github.seenings.sys.constant.SeenConstant.FEIGN_VERSION;

/**
 * HttpUserMaritalService
 *
 * @author chixuehui
 * @since 2023-02-11
 */
@HttpExchange(
        value = FEIGN_VERSION + "user/user-marital")
public interface HttpUserMaritalService {
    @PostExchange("user-id-to-marital-status")
    Map<Long, Integer> userIdToMaritalStatus(@RequestBody Set<Long> userIds);

    @PostExchange("set")
    boolean set(@RequestParam("userId") Long userId, @RequestParam("maritalStatus") Integer maritalStatus);
}
