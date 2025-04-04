package com.songchi.seen.work.controller;

import com.songchi.seen.info.http.HttpUserWorkPositionService;
import com.songchi.seen.info.service.UserWorkPositionService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

import static com.songchi.seen.sys.constant.SeenConstant.FEIGN_VERSION;

/**
 * UserWorkPositionController
 *
 * @author chixuehui
 * @since 2022-10-16
 */
@RestController
@RequestMapping(FEIGN_VERSION + "user/user-work-position")
public class UserWorkPositionController implements HttpUserWorkPositionService {

    @Resource
    private UserWorkPositionService userWorkPositionService;

    @Override
    @PostMapping("user-id-to-position")
    public Map<Long, Integer> userIdToPosition(@RequestBody Set<Long> userIds) {
        return userWorkPositionService.userIdToPosition(userIds);
    }

    @Override
    @PostMapping("set")
    public boolean set(@RequestParam("userId") Long userId, @RequestParam("position") Integer position) {

        return userWorkPositionService.set(userId, position);
    }
}
