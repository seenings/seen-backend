package io.github.seenings.work.controller;

import io.github.seenings.work.http.HttpUserWorkService;
import io.github.seenings.work.service.UserWorkCompanyService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Set;

/**
 * UserWorkController
 *
 * @author chixuehui
 * @since 2022-12-03
 */
@RestController
@AllArgsConstructor
public class UserWorkController implements HttpUserWorkService {

    private UserWorkCompanyService userWorkCompanyService;

    @Override
    public Map<Long, String> userIdToCompanyName(Set<Long> userIds) {
        return userWorkCompanyService.userIdToCompanyName(userIds);
    }

    @Override
    public boolean set(@RequestParam("userId") Long userId, @RequestParam("companyName") String companyName) {

        return userWorkCompanyService.set(userId, companyName);
    }
}
