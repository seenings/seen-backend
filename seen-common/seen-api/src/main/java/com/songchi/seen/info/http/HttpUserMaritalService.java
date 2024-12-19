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
    Map<Integer, Integer> userIdToMaritalStatus(@RequestBody Set<Integer> userIds);

    @PostMapping("set")
    boolean set(@RequestParam("userId") Integer userId, @RequestParam("maritalStatus") Integer maritalStatus);
}
