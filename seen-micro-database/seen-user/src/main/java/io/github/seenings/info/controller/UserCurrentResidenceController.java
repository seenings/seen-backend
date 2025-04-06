package io.github.seenings.info.controller;

import io.github.seenings.info.http.HttpUserCurrentResidenceService;
import io.github.seenings.info.service.UserCurrentResidenceService;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static io.github.seenings.sys.constant.SeenConstant.FEIGN_VERSION;

/**
 * UserCurrentResidenceController
 *
 * @author chixuehui
 * @since 2022-10-16
 */
@RestController
@RequestMapping(FEIGN_VERSION + "user/user-current-residence")
public class UserCurrentResidenceController implements HttpUserCurrentResidenceService {

    @Resource
    private UserCurrentResidenceService userCurrentResidenceService;

    @Override
    @PostMapping("user-id-to-city-id")
    public Map<Long, Integer> userIdToCityId(@RequestBody Set<Long> userIds) {
        return userCurrentResidenceService.userIdToCityId(userIds);
    }

    @Override
    @PostMapping("user-id-to-province-id")
    public Map<Long, Integer> userIdToProvinceId(@RequestBody Set<Long> userIds) {
        return userCurrentResidenceService.userIdToProvinceId(userIds);
    }

    @Override
    @PostMapping("set")
    public boolean set(
            @RequestParam("userId") Long userId,
            @RequestParam("provinceId") Integer provinceId,
            @RequestParam("cityId") Integer cityId) {
        return userCurrentResidenceService.set(userId, provinceId, cityId);
    }

    @Override
    @PostMapping("current-residence-city-to-user-id")
    public List<Long> currentResidenceCityToUserId(
            @RequestParam("cityId") Integer cityId,
            @RequestParam("current") int current,
            @RequestParam("size") int size) {
        return userCurrentResidenceService.currentResidenceCityToUserId(cityId, current, size);
    }
}
