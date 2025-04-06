package io.github.seenings.info.controller;

import io.github.seenings.info.http.HttpUserWeightService;
import io.github.seenings.info.service.UserWeightService;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.Map;
import java.util.Set;

import static io.github.seenings.sys.constant.SeenConstant.FEIGN_VERSION;

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
    public Map<Long, Integer> userIdToWeightKg(@RequestBody Set<Long> userIds) {
        return userWeightService.userIdToWeightKg(userIds);
    }

    @Override
    @PostMapping("set")
    public boolean set(@RequestParam("userId") Long userId, @RequestParam("weightKg") Integer weightKg) {
        return userWeightService.set(userId, weightKg);
    }
}
