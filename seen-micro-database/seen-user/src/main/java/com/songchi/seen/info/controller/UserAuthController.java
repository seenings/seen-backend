package com.songchi.seen.info.controller;

import com.songchi.seen.info.http.HttpUserAuthService;
import com.songchi.seen.info.service.UserAuthService;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.Map;
import java.util.Set;

import static com.songchi.seen.sys.constant.SeenConstant.FEIGN_VERSION;

/**
 * UserAuthController
 *
 * @author chixuehui
 * @since 2022-10-16
 */
@RestController
@RequestMapping(FEIGN_VERSION + "user/user-auth")
public class UserAuthController implements HttpUserAuthService {
    @Resource
    private UserAuthService userAuthService;

    @Override
    @PostMapping("user-id-to-user-auth")
    public Map<Integer, Integer> userIdToUserAuth(@RequestBody Set<Integer> userIds) {
        return userAuthService.userIdToUserAuth(userIds);
    }

    @Override
    @PostMapping("set")
    public boolean set(@RequestParam("userId") Integer userId, @RequestParam("authStatus") Integer authStatus) {
        return userAuthService.set(userId, authStatus);
    }
}
