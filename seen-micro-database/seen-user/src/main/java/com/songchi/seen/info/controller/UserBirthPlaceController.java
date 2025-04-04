package com.songchi.seen.info.controller;

import com.songchi.seen.info.http.HttpUserBirthPlaceService;
import com.songchi.seen.info.service.UserBirthPlaceService;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.Map;
import java.util.Set;

import static com.songchi.seen.sys.constant.SeenConstant.FEIGN_VERSION;

/**
 * UserBirthPlaceController
 *
 * @author chixuehui
 * @since 2022-10-16
 */
@RestController
@RequestMapping(FEIGN_VERSION + "user/user-birth-place")
public class UserBirthPlaceController implements HttpUserBirthPlaceService {
    @Resource
    private UserBirthPlaceService userBirthPlaceService;

    @Override
    @PostMapping("user-id-to-city-id")
    public Map<Long, Integer> userIdToCityId(@RequestBody Set<Long> userIds) {
        return userBirthPlaceService.userIdToCityId(userIds);
    }

    @Override
    @PostMapping("user-id-to-province-id")
    public Map<Long, Integer> userIdToProvinceId(@RequestBody Set<Long> userIds) {
        return userBirthPlaceService.userIdToProvinceId(userIds);
    }

    @Override
    @PostMapping("set")
    public boolean set(
            @RequestParam("userId") Long userId,
            @RequestParam("provinceId") Integer provinceId,
            @RequestParam("cityId") Integer cityId) {
        return userBirthPlaceService.set(userId, provinceId, cityId);
    }
}
