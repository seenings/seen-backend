package io.github.seenings.info.http;

import io.github.seenings.sys.constant.SeenConstant;
import io.github.seenings.sys.constant.ServiceNameConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.Set;

/**
 * HttpUserBirthdayService
 *
 * @author chixuehui
 * @since 2022-10-07
 */
@FeignClient(
        name = ServiceNameConstant.SERVICE_SEEN_USER,
        path = SeenConstant.FEIGN_VERSION + "user/birthday",
        contextId = "HttpUserBirthdayService")
public interface HttpUserBirthdayService {
    @PostMapping("user-id-to-year")
    Map<Long, Integer> userIdToYear(@RequestBody Set<Long> userIds);

    @PostMapping("user-id-to-month")
    Map<Long, Integer> userIdToMonth(@RequestBody Set<Long> userIds);

    @PostMapping("user-id-to-day")
    Map<Long, Integer> userIdToDay(@RequestBody Set<Long> userIds);

    @PostMapping("set")
    boolean set(
            @RequestParam("userId") Long userId,
            @RequestParam("year") Integer year,
            @RequestParam(value = "month", required = false) Integer month,
            @RequestParam(value = "day", required = false) Integer day);
}
