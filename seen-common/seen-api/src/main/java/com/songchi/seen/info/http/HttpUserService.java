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
 * HttpUserService
 */
@FeignClient(name = ServiceNameConstant.SERVICE_SEEN_USER, contextId = "HttpUserService", path = FEIGN_VERSION + "user/user")
public interface HttpUserService {
    @PostMapping("user-id-to-phone-number")
    Map<Long, String> userIdToPhoneNumber(@RequestBody Set<Long> userIds);

    @PostMapping("phone-number-to-user-id")
    Map<String, Long> phoneNumberToUserId(@RequestBody Set<String> phoneNumbers);

    @PostMapping("set")
    Long set(@RequestParam("phoneNumber") String phoneNumber);
}
