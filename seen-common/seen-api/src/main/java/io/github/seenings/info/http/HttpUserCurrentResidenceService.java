package io.github.seenings.info.http;

import io.github.seenings.sys.constant.SeenConstant;
import io.github.seenings.sys.constant.ServiceNameConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * HttpUserCurrentResidenceService
 *
 * @author chixuehui
 * @since 2022-10-16
 */
@HttpExchange(SeenConstant.FEIGN_VERSION + "user/user-current-residence")
public interface HttpUserCurrentResidenceService {
    @PostExchange("user-id-to-city-id")
    Map<Long, Integer> userIdToCityId(@RequestBody Set<Long> userIds);

    @PostExchange("user-id-to-province-id")
    Map<Long, Integer> userIdToProvinceId(@RequestBody Set<Long> userIds);

    @PostExchange("set")
    boolean set(@RequestParam("userId") Long userId, @RequestParam("provinceId") Integer provinceId,
                @RequestParam("cityId") Integer cityId);

    @PostExchange("current-residence-city-to-user-id")
    List<Long> currentResidenceCityToUserId
            (@RequestParam("cityId") Integer cityId,
             @RequestParam("current") int current, @RequestParam("size") int size);
}
