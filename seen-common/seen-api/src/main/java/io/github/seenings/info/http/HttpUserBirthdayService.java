package io.github.seenings.info.http;

import io.github.seenings.sys.constant.SeenConstant;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.Map;
import java.util.Set;

/**
 * HttpUserBirthdayService
 *
 * @author chixuehui
 * @since 2022-10-07
 */
@HttpExchange(
        value =SeenConstant.FEIGN_VERSION + "user/birthday")
public interface HttpUserBirthdayService {
    @PostExchange("user-id-to-year")
    Map<Long, Integer> userIdToYear(@RequestBody Set<Long> userIds);

    @PostExchange("user-id-to-month")
    Map<Long, Integer> userIdToMonth(@RequestBody Set<Long> userIds);

    @PostExchange("user-id-to-day")
    Map<Long, Integer> userIdToDay(@RequestBody Set<Long> userIds);

    @PostExchange("set")
    boolean set(
            @RequestParam("userId") Long userId,
            @RequestParam("year") Integer year,
            @RequestParam(value = "month", required = false) Integer month,
            @RequestParam(value = "day", required = false) Integer day);
}
