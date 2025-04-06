package io.github.seenings.info.controller;

import io.github.seenings.info.http.HttpUserBirthdayService;
import io.github.seenings.info.service.UserBirthdayService;
import io.github.seenings.sys.constant.SeenConstant;
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
    public Map<Long, Integer> userIdToYear(@RequestBody Set<Long> userIds) {
        return userBirthdayService.userIdToYear(userIds);
    }

    @Override
    @PostMapping("user-id-to-month")
    public Map<Long, Integer> userIdToMonth(@RequestBody Set<Long> userIds) {
        return userBirthdayService.userIdToMonth(userIds);
    }

    @Override
    @PostMapping("user-id-to-day")
    public Map<Long, Integer> userIdToDay(@RequestBody Set<Long> userIds) {
        return userBirthdayService.userIdToDay(userIds);
    }

    @Override
    @PostMapping("set")
    public boolean set(
            @RequestParam("userId") Long userId,
            @RequestParam("year") Integer year,
            @RequestParam(value = "month", required = false) Integer month,
            @RequestParam(value = "day", required = false) Integer day) {
        return userBirthdayService.set(userId, year, month, day);
    }
}
