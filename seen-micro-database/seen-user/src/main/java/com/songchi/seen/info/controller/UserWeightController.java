package com.songchi.seen.info.controller;

import com.songchi.seen.info.http.HttpUserWeightService;
import com.songchi.seen.info.service.UserWeightService;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.Map;
import java.util.Set;

import static com.songchi.seen.sys.constant.SeenConstant.FEIGN_VERSION;

/**
 * UserWeightController
 *
 * @author chixuehui
 * @since 2022-10-16
 */
@RestController
@RequestMapping(FEIGN_VERSION + "user/user-weight")
public class UserWeightController implements HttpUserWeightService {
    @Resource
    private UserWeightService userWeightService;

    @Override
    @PostMapping("user-id-to-weight-kg")
    public Map<Integer, Integer> userIdToWeightKg(@RequestBody Set<Integer> userIds) {
        return userWeightService.userIdToWeightKg(userIds);
    }

    @Override
    @PostMapping("set")
    public boolean set(@RequestParam("userId") Integer userId, @RequestParam("weightKg") Integer weightKg) {
        return userWeightService.set(userId, weightKg);
    }
}
