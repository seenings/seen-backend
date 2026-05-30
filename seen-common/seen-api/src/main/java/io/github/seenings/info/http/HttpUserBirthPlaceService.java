package io.github.seenings.info.http;

import io.github.seenings.sys.constant.SeenConstant;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.Map;
import java.util.Set;

/**
 * HttpUserBirthPlaceService
 *
 * @author chixuehui
 * @since 2022-10-16
 */
@HttpExchange(
        value = SeenConstant.FEIGN_VERSION + "user/user-birth-place")
public interface HttpUserBirthPlaceService {

    @PostExchange("user-id-to-city-id")
    Map<Long, Integer> userIdToCityId(Set<Long> userIds);

    @PostExchange("user-id-to-province-id")
    Map<Long, Integer> userIdToProvinceId(@RequestBody Set<Long> userIds);

    @PostExchange("set")
    boolean set(@RequestParam("userId") Long userId, @RequestParam("provinceId") Integer provinceId,
                @RequestParam("cityId")
                Integer cityId);
}
