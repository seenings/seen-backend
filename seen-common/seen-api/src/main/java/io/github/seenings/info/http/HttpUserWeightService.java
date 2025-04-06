package io.github.seenings.info.http;

import io.github.seenings.sys.constant.ServiceNameConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.Set;

import static io.github.seenings.sys.constant.SeenConstant.FEIGN_VERSION;

/**
 * HttpUserWeightService
 *
 * @author chixuehui
 * @since 2022-10-16
 */
@FeignClient(
        name = ServiceNameConstant.SERVICE_SEEN_USER,
        path = FEIGN_VERSION + "user/user-weight",
        contextId = "HttpUserWeightService")
public interface HttpUserWeightService {
    @PostMapping("user-id-to-weight-kg")
    Map<Long, Integer> userIdToWeightKg(@RequestBody Set<Long> userIds);

    @PostMapping("set")
    boolean set(@RequestParam("userId") Long userId, @RequestParam("weightKg") Integer weightKg);
}
