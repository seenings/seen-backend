package com.songchi.seen.info.http;

import com.songchi.seen.sys.constant.SeenConstant;
import com.songchi.seen.sys.constant.ServiceNameConstant;
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
    Map<Integer, Integer> userIdToYear(@RequestBody Set<Integer> userIds);

    @PostMapping("user-id-to-month")
    Map<Integer, Integer> userIdToMonth(@RequestBody Set<Integer> userIds);

    @PostMapping("user-id-to-day")
    Map<Integer, Integer> userIdToDay(@RequestBody Set<Integer> userIds);

    @PostMapping("set")
    boolean set(
            @RequestParam("userId") Integer userId,
            @RequestParam("year") Integer year,
            @RequestParam(value = "month", required = false) Integer month,
            @RequestParam(value = "day", required = false) Integer day);
}
