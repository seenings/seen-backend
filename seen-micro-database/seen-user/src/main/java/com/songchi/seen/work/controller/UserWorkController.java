package com.songchi.seen.work.controller;

import com.songchi.seen.work.http.HttpUserWorkService;
import com.songchi.seen.work.service.UserWorkCompanyService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Set;

import static com.songchi.seen.sys.constant.SeenConstant.FEIGN_VERSION;

/**
 * UserWorkController
 *
 * @author chixuehui
 * @since 2022-12-03
 */
@RestController
@RequestMapping(FEIGN_VERSION + "user/work")
public class UserWorkController implements HttpUserWorkService {

    @Resource
    private UserWorkCompanyService userWorkCompanyService;

    @Override
    @PostMapping("user-id-to-company-name")
    public Map<Long, String> userIdToCompanyName(Set<Long> userIds) {
        return userWorkCompanyService.userIdToCompanyName(userIds);
    }

    @Override
    @PostMapping("set")
    public boolean set(@RequestParam("userId") Long userId, @RequestParam("companyName") String companyName) {

        return userWorkCompanyService.set(userId, companyName);
    }
}
