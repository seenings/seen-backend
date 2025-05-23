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
 * HttpUserMaritalService
 *
 * @author chixuehui
 * @since 2023-02-11
 */
@FeignClient(
        name = ServiceNameConstant.SERVICE_SEEN_USER,
        contextId = "HttpUserMaritalService",
        path = FEIGN_VERSION + "user/user-marital")
public interface HttpUserMaritalService {
    @PostMapping("user-id-to-marital-status")
    Map<Long, Integer> userIdToMaritalStatus(@RequestBody Set<Long> userIds);

    @PostMapping("set")
    boolean set(@RequestParam("userId") Long userId, @RequestParam("maritalStatus") Integer maritalStatus);
}
