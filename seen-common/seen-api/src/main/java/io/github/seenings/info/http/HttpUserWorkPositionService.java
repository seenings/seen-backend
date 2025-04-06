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
 * HttpUserWorkPositionService
 *
 * @author chixuehui
 * @since 2022-10-16
 */

@FeignClient(
        name = ServiceNameConstant.SERVICE_SEEN_USER,
        path = SeenConstant.FEIGN_VERSION + "user/user-work-position",
        contextId = "HttpUserWorkPositionService")
public interface HttpUserWorkPositionService {
    @PostMapping("user-id-to-position")
    Map<Long, Integer> userIdToPosition(@RequestBody Set<Long> userIds);

    @PostMapping("set")
    boolean set(@RequestParam("userId") Long userId,@RequestParam("position") Integer position);
}
