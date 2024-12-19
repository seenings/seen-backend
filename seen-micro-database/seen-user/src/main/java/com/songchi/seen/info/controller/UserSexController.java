package com.songchi.seen.info.controller;

import com.songchi.seen.sys.constant.SeenConstant;
import com.songchi.seen.info.enumeration.Sex;
import com.songchi.seen.info.http.HttpUserSexService;
import com.songchi.seen.info.service.UserSexService;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * UserSexController
 *
 * @author chixuehui
 * @since 2022-10-07
 */
@RestController
@RequestMapping(SeenConstant.FEIGN_VERSION + "user/sex")
public class UserSexController implements HttpUserSexService {

    @Resource
    private UserSexService userSexService;

    @Override
    @PostMapping("user-id-to-sex")
    public Map<Integer, Integer> userIdToSex(@RequestBody Set<Integer> userIds) {
        return userSexService.userIdToSex(userIds);
    }

    @Override
    @PostMapping("set")
    public boolean set(@RequestParam Integer userId, @RequestParam Sex sex) {
        return userSexService.set(userId, sex);
    }

    @Override
    @PostMapping("sex-to-user-id")
    public List<Integer> sexToUserId(
            @RequestParam("sex") Sex sex, @RequestParam("current") int current, @RequestParam("size") int size) {
        return userSexService.sexToUserId(sex, current, size);
    }

    @Override
    @PostMapping("sex-count")
    public long sexCount(@RequestParam("sex") Sex sex) {
        return userSexService.sexCount(sex);
    }
}
