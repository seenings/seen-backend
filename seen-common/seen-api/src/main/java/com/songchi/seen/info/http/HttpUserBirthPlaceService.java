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
 * HttpUserBirthPlaceService
 *
 * @author chixuehui
 * @since 2022-10-16
 */
@FeignClient(
        name = ServiceNameConstant.SERVICE_SEEN_USER,
        path = SeenConstant.FEIGN_VERSION + "user/user-birth-place",
        contextId = "HttpUserBirthPlaceService")
public interface HttpUserBirthPlaceService {

    @PostMapping("user-id-to-city-id")
    Map<Integer, Integer> userIdToCityId(Set<Integer> userIds);

    @PostMapping("user-id-to-province-id")
    Map<Integer, Integer> userIdToProvinceId(@RequestBody Set<Integer> userIds);

    @PostMapping("set")
    boolean set(@RequestParam("userId") Integer userId, @RequestParam("provinceId") Integer provinceId,
                @RequestParam("cityId")
                Integer cityId);
}
