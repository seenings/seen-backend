package com.songchi.seen.info.controller;

import com.songchi.seen.info.http.HttpUserMaritalService;
import com.songchi.seen.info.service.UserMaritalService;
import com.songchi.seen.info.util.UserEnumUtils;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

import static com.songchi.seen.sys.constant.SeenConstant.FEIGN_VERSION;

/**
 *
 *
 * @author chixuehui
 * @since 2023-02-11
 */
@RestController
@RequestMapping(FEIGN_VERSION + "user/user-marital")
public class UserMaritalController implements HttpUserMaritalService {
    @Resource
    private UserMaritalService userMaritalService;

    @Override
    @PostMapping("user-id-to-marital-status")
    public Map<Long, Integer> userIdToMaritalStatus(@RequestBody Set<Long> userIds) {
        return userMaritalService.userIdToMaritalStatus(userIds);
    }

    @Override
    @PostMapping("set")
    public boolean set(@RequestParam("userId") Long userId, @RequestParam("maritalStatus") Integer maritalStatus) {
        return userMaritalService.set(userId, UserEnumUtils.indexToMaritalStatus(maritalStatus));
    }
}
