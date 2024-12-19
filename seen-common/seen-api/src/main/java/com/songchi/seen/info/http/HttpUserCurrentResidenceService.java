package com.songchi.seen.info.http;

import com.songchi.seen.sys.constant.SeenConstant;
import com.songchi.seen.sys.constant.ServiceNameConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * HttpUserCurrentResidenceService
 *
 * @author chixuehui
 * @since 2022-10-16
 */
@FeignClient(
        name = ServiceNameConstant.SERVICE_SEEN_USER,
        path = SeenConstant.FEIGN_VERSION + "user/user-current-residence",
        contextId = "HttpUserCurrentResidenceService")
public interface HttpUserCurrentResidenceService {
    @PostMapping("user-id-to-city-id")
    Map<Integer, Integer> userIdToCityId(@RequestBody Set<Integer> userIds);

    @PostMapping("user-id-to-province-id")
    Map<Integer, Integer> userIdToProvinceId(@RequestBody Set<Integer> userIds);

    @PostMapping("set")
    boolean set(@RequestParam("userId") Integer userId, @RequestParam("provinceId") Integer provinceId,
                @RequestParam("cityId") Integer cityId);

    @PostMapping("current-residence-city-to-user-id")
    List<Integer> currentResidenceCityToUserId
            (@RequestParam("cityId") Integer cityId,
             @RequestParam("current") int current, @RequestParam("size") int size);
}
