package io.github.seenings.info.controller;

import io.github.seenings.info.http.HttpUserStatureService;
import io.github.seenings.info.service.UserStatureService;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.Map;
import java.util.Set;

import static io.github.seenings.sys.constant.SeenConstant.FEIGN_VERSION;

/**
 * UserStatureController
 *
 * @author chixuehui
 * @since 2022-10-16
 */
@RestController
@RequestMapping(FEIGN_VERSION + "user/user-stature")
public class UserStatureController implements HttpUserStatureService {
    @Resource
    private UserStatureService userStatureService;

    @Override
    @PostMapping("user-id-to-stature-cm")
    public Map<Long, Integer> userIdToStatureCm(@RequestBody Set<Long> userIds) {
        return userStatureService.userIdToStatureCm(userIds);
    }

    @Override
    @PostMapping("set")
    public boolean set(@RequestParam("userId") Long userId, @RequestParam("statureCm") Integer statureCm) {
        return userStatureService.set(userId, statureCm);
    }
}
