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
 * HttpUserAliasNameService
 *
 * @author chixuehui
 * @since 2022-10-16
 */

@FeignClient(
        name = ServiceNameConstant.SERVICE_SEEN_USER,
        path = SeenConstant.FEIGN_VERSION + "user/user-alias-name",
        contextId = "HttpUserAliasNameService")
public interface HttpUserAliasNameService {
    @PostMapping("user-id-to-alias-name")
    Map<Integer, String> userIdToAliasName(@RequestBody Set<Integer> userIds);

    @PostMapping("set")
    boolean set(@RequestParam("userId") Integer userId, @RequestParam("aliasName") String aliasName);
}
