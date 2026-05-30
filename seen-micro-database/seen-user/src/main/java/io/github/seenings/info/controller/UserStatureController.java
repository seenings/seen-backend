package io.github.seenings.info.controller;

import io.github.seenings.info.http.HttpUserStatureService;
import io.github.seenings.info.service.UserStatureService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

/**
 * UserStatureController
 *
 * @author chixuehui
 * @since 2022-10-16
 */
@RestController
@AllArgsConstructor
public class UserStatureController implements HttpUserStatureService {
    private UserStatureService userStatureService;

    @Override
    public Map<Long, Integer> userIdToStatureCm(@RequestBody Set<Long> userIds) {
        return userStatureService.userIdToStatureCm(userIds);
    }

    @Override
    public boolean set(@RequestParam("userId") Long userId, @RequestParam("statureCm") Integer statureCm) {
        return userStatureService.set(userId, statureCm);
    }
}
