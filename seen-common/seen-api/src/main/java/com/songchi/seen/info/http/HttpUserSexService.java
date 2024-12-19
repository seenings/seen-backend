package com.songchi.seen.info.http;

import com.songchi.seen.sys.constant.SeenConstant;
import com.songchi.seen.sys.constant.ServiceNameConstant;
import com.songchi.seen.info.enumeration.Sex;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * UserSexService
 *
 * @author chixuehui
 * @since 2022-10-06
 */
@FeignClient(
        name = ServiceNameConstant.SERVICE_SEEN_USER,
        path = SeenConstant.FEIGN_VERSION + "user/sex",
        contextId = "HttpUserSexService")
public interface HttpUserSexService {
    @PostMapping("user-id-to-sex")
    Map<Integer, Integer> userIdToSex(@RequestBody Set<Integer> userIds);

    @PostMapping("set")
    boolean set(@RequestParam("userId") Integer userId, @RequestParam("sex") Sex sex);

    @PostMapping("sex-to-user-id")
    List<Integer> sexToUserId(
            @RequestParam("sex") Sex sex, @RequestParam("current") int current, @RequestParam("size") int size);

    @PostMapping("sex-count")
    long sexCount(@RequestParam("sex") Sex sex);
}
