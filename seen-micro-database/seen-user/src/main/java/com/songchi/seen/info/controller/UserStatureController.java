package com.songchi.seen.info.controller;

import com.songchi.seen.info.http.HttpUserStatureService;
import com.songchi.seen.info.service.UserStatureService;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.Map;
import java.util.Set;

import static com.songchi.seen.sys.constant.SeenConstant.FEIGN_VERSION;

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
    public Map<Integer, Integer> userIdToStatureCm(@RequestBody Set<Integer> userIds) {
        return userStatureService.userIdToStatureCm(userIds);
    }

    @Override
    @PostMapping("set")
    public boolean set(@RequestParam("userId") Integer userId, @RequestParam("statureCm") Integer statureCm) {
        return userStatureService.set(userId, statureCm);
    }
}
