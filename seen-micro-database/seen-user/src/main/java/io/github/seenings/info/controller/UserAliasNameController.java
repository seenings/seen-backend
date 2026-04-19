package io.github.seenings.info.controller;

import io.github.seenings.info.http.HttpUserAliasNameService;
import io.github.seenings.info.service.UserAliasNameService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

/**
 * 昵称
 */
@RestController
@AllArgsConstructor
public class UserAliasNameController implements HttpUserAliasNameService {
    private UserAliasNameService userAliasNameService;

    @Override
    public Map<Long, String> userIdToAliasName(Set<Long> userIds) {
        return userAliasNameService.userIdToAliasName(userIds);
    }

    @Override
    public boolean set(Long userId, String aliasName) {
        return userAliasNameService.set(userId, aliasName);
    }
}
