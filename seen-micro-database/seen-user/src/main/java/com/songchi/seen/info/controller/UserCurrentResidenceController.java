package com.songchi.seen.info.controller;

import com.songchi.seen.info.http.HttpUserCurrentResidenceService;
import com.songchi.seen.info.service.UserCurrentResidenceService;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.songchi.seen.sys.constant.SeenConstant.FEIGN_VERSION;

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
    public Map<Integer, Integer> userIdToCityId(@RequestBody Set<Integer> userIds) {
        return userCurrentResidenceService.userIdToCityId(userIds);
    }

    @Override
    @PostMapping("user-id-to-province-id")
    public Map<Integer, Integer>userIdToProvinceId(@RequestBody Set<Integer> userIds) {
        return userCurrentResidenceService.userIdToProvinceId(userIds);
    }

    @Override
    @PostMapping("set")
    public boolean set(
            @RequestParam("userId") Integer userId,
            @RequestParam("provinceId") Integer provinceId,
            @RequestParam("cityId") Integer cityId) {
        return userCurrentResidenceService.set(userId, provinceId, cityId);
    }

    @Override
    @PostMapping("current-residence-city-to-user-id")
    public List<Integer> currentResidenceCityToUserId(
            @RequestParam("cityId") Integer cityId,
            @RequestParam("current") int current,
            @RequestParam("size") int size) {
        return userCurrentResidenceService.currentResidenceCityToUserId(cityId, current, size);
    }
}
