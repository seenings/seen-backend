package com.songchi.seen.info.controller;

import com.songchi.seen.info.http.HttpUserAliasNameService;
import com.songchi.seen.info.service.UserAliasNameService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

import static com.songchi.seen.sys.constant.SeenConstant.FEIGN_VERSION;

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
    public Map<Integer, String> userIdToAliasName(@RequestBody Set<Integer> userIds) {
        return userAliasNameService.userIdToAliasName(userIds);
    }

    @Override
    @PostMapping("set")
    public boolean set(@RequestParam("userId") Integer userId, @RequestParam("aliasName") String aliasName) {
        return userAliasNameService.set(userId, aliasName);
    }
}
