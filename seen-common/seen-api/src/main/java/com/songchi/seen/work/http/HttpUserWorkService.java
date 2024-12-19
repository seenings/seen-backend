package com.songchi.seen.work.http;

import java.util.Map;
import java.util.Set;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.songchi.seen.sys.constant.SeenConstant;
import com.songchi.seen.sys.constant.ServiceNameConstant;

/**
 * HttpUserWorkService
 *
 * @author chixuehui
 * @since 2022-12-03
 */
@FeignClient(
        name = ServiceNameConstant.SERVICE_SEEN_USER,
        path = SeenConstant.FEIGN_VERSION + "user/work",
        contextId = "HttpUserWorkService")
public interface HttpUserWorkService {
    @PostMapping("user-id-to-company-name")
    Map<Integer, String> userIdToCompanyName(@RequestBody Set<Integer> userIds);

    @PostMapping("set")
    boolean set(@RequestParam("userId") Integer userId, @RequestParam("companyName") String companyName);
}
