package com.songchi.seen.info.controller;

import com.songchi.seen.info.http.HttpUserBirthdayService;
import com.songchi.seen.info.service.UserBirthdayService;
import com.songchi.seen.sys.constant.SeenConstant;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.Map;
import java.util.Set;

/**
 * UserSexController
 *
 * @author chixuehui
 * @since 2022-10-07
 */
@RestController
@RequestMapping(SeenConstant.FEIGN_VERSION + "user/birthday")
public class UserBirthdayController implements HttpUserBirthdayService {

    @Resource
    private UserBirthdayService userBirthdayService;

    @Override
    @PostMapping("user-id-to-year")
    public Map<Integer, Integer> userIdToYear(@RequestBody Set<Integer> userIds) {
        return userBirthdayService.userIdToYear(userIds);
    }

    @Override
    @PostMapping("user-id-to-month")
    public Map<Integer, Integer> userIdToMonth(@RequestBody Set<Integer> userIds) {
        return userBirthdayService.userIdToMonth(userIds);
    }

    @Override
    @PostMapping("user-id-to-day")
    public Map<Integer, Integer> userIdToDay(@RequestBody Set<Integer> userIds) {
        return userBirthdayService.userIdToDay(userIds);
    }

    @Override
    @PostMapping("set")
    public boolean set(
            @RequestParam("userId") Integer userId,
            @RequestParam("year") Integer year,
            @RequestParam(value = "month", required = false) Integer month,
            @RequestParam(value = "day", required = false) Integer day) {
        return userBirthdayService.set(userId, year, month, day);
    }
}
