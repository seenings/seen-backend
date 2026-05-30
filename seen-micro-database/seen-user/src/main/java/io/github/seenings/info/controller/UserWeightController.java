package io.github.seenings.info.controller;

import io.github.seenings.info.http.HttpUserWeightService;
import io.github.seenings.info.service.UserWeightService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

/**
 * UserWeightController
 *
 * @author chixuehui
 * @since 2022-10-16
 */
@RestController
@AllArgsConstructor
public class UserWeightController implements HttpUserWeightService {
    private UserWeightService userWeightService;

    @Override
    public Map<Long, Integer> userIdToWeightKg(@RequestBody Set<Long> userIds) {
        return userWeightService.userIdToWeightKg(userIds);
    }

    @Override
    public boolean set(@RequestParam("userId") Long userId, @RequestParam("weightKg") Integer weightKg) {
        return userWeightService.set(userId, weightKg);
    }
}
