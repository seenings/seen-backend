package com.songchi.seen.info.http;

import com.songchi.seen.sys.constant.ServiceNameConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.Set;

import static com.songchi.seen.sys.constant.SeenConstant.FEIGN_VERSION;

/**
 * HttpUserStatureService
 *
 * @author chixuehui
 * @since 2022-10-16
 */
@FeignClient(
        name = ServiceNameConstant.SERVICE_SEEN_USER,
        contextId = "HttpUserStatureService",
        path = FEIGN_VERSION + "user/user-stature")
public interface HttpUserStatureService {

    @PostMapping("user-id-to-stature-cm")
    Map<Long, Integer> userIdToStatureCm(@RequestBody Set<Long> userIds);

    @PostMapping("set")
    boolean set(@RequestParam("userId") Long userId, @RequestParam("statureCm") Integer statureCm);
}
