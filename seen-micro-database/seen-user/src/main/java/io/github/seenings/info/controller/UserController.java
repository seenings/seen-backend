package io.github.seenings.info.controller;

import io.github.seenings.info.http.HttpUserService;
import io.github.seenings.info.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

import static io.github.seenings.sys.constant.SeenConstant.FEIGN_VERSION;

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
    public Map<Long, String> userIdToPhoneNumber(@RequestBody Set<Long> userIds) {
        return userService.userIdToPhoneNumber(userIds);
    }

    @Override
    @PostMapping("phone-number-to-user-id")
    public Map<String, Long> phoneNumberToUserId(@RequestBody Set<String> phoneNumbers) {
        return userService.phoneNumberToUserId(phoneNumbers);
    }

    @Override
    @PostMapping("set")
    public Long set(@RequestParam("phoneNumber") String phoneNumber) {
        return userService.set(phoneNumber);
    }
}
