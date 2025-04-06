package io.github.seenings.info.controller;

import io.github.seenings.info.http.HttpUserAliasNameService;
import io.github.seenings.info.service.UserAliasNameService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

import static io.github.seenings.sys.constant.SeenConstant.FEIGN_VERSION;

/**
 * UserAliasNameController
 *
 * @author chixuehui
 * @since 2022-10-16
 */
@RestController
@RequestMapping(FEIGN_VERSION + "user/user-alias-name")
public class UserAliasNameController implements HttpUserAliasNameService {
    @Resource
    private UserAliasNameService userAliasNameService;

    @Override
    @PostMapping("user-id-to-alias-name")
    public Map<Long, String> userIdToAliasName(@RequestBody Set<Long> userIds) {
        return userAliasNameService.userIdToAliasName(userIds);
    }

    @Override
    @PostMapping("set")
    public boolean set(@RequestParam("userId") Long userId, @RequestParam("aliasName") String aliasName) {
        return userAliasNameService.set(userId, aliasName);
    }
}
