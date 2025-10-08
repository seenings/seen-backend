package io.github.seenings.info.controller;

import io.github.seenings.info.http.UserController;
import io.github.seenings.info.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

/**
 * 用户信息
 */
@RestController
@AllArgsConstructor
public class UserControllerImpl implements UserController {
    /**
     * 用户信息
     */
    private UserService userService;

    @Override
    public Map<Long, String> userIdToPhoneNumber(Set<Long> userIds) {
        return userService.userIdToPhoneNumber(userIds);
    }

    @Override
    public Map<String, Long> phoneNumberToUserId(Set<String> phoneNumbers) {
        return userService.phoneNumberToUserId(phoneNumbers);
    }

    @Override
    public Long set(String phoneNumber) {
        return userService.set(phoneNumber);
    }
}
