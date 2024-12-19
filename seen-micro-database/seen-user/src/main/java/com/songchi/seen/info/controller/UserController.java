package com.songchi.seen.info.controller;

import com.songchi.seen.info.http.HttpUserService;
import com.songchi.seen.info.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

import static com.songchi.seen.sys.constant.SeenConstant.FEIGN_VERSION;

/**
 * UserController
 *
 * @author chixuehui
 * @since 2023-02-11
 */
@RestController
@RequestMapping(FEIGN_VERSION + "user/user")
public class UserController implements HttpUserService {

    @Resource
    private UserService userService;

    @Override
    @PostMapping("user-id-to-phone-number")
    public Map<Integer, String> userIdToPhoneNumber(@RequestBody Set<Integer> userIds) {
        return userService.userIdToPhoneNumber(userIds);
    }

    @Override
    @PostMapping("phone-number-to-user-id")
    public Map<String, Integer> phoneNumberToUserId(@RequestBody Set<String> phoneNumbers) {
        return userService.phoneNumberToUserId(phoneNumbers);
    }

    @Override
    @PostMapping("set")
    public Integer set(@RequestParam("phoneNumber") String phoneNumber) {
        return userService.set(phoneNumber);
    }
}
