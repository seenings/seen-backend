package io.github.seenings.info.controller;

import io.github.seenings.sys.constant.SeenConstant;
import io.github.seenings.info.enumeration.Sex;
import io.github.seenings.info.http.HttpUserSexService;
import io.github.seenings.info.service.UserSexService;
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
    public Map<Long, Integer> userIdToSex(@RequestBody Set<Long> userIds) {
        return userSexService.userIdToSex(userIds);
    }

    @Override
    @PostMapping("set")
    public boolean set(@RequestParam Long userId, @RequestParam Sex sex) {
        return userSexService.set(userId, sex);
    }

    @Override
    @PostMapping("sex-to-user-id")
    public List<Long> sexToUserId(
            @RequestParam("sex") Sex sex, @RequestParam("current") int current, @RequestParam("size") int size) {
        return userSexService.sexToUserId(sex, current, size);
    }

    @Override
    @PostMapping("sex-count")
    public long sexCount(@RequestParam("sex") Sex sex) {
        return userSexService.sexCount(sex);
    }
}
