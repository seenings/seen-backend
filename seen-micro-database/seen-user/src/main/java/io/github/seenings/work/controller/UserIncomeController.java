package io.github.seenings.work.controller;

import io.github.seenings.info.enumeration.YearIncomeEnum;
import io.github.seenings.work.http.HttpUserIncomeService;
import io.github.seenings.work.service.UserIncomeService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

import static io.github.seenings.sys.constant.SeenConstant.FEIGN_VERSION;

/**
 * UserWorkController
 *
 * @author chixuehui
 * @since 2022-12-03
 */
@RestController
@RequestMapping(FEIGN_VERSION + "user/user-income")
public class UserIncomeController implements HttpUserIncomeService {

    @Resource
    private UserIncomeService userIncomeService;

    @Override
    @PostMapping("user-id-to-annual-income")
    public Map<Long, Integer> userIdToAnnualIncome(@RequestBody Set<Long> userIds) {
        return userIncomeService.userIdToAnnualIncome(userIds);
    }

    @Override
    @PostMapping("set")
    public boolean set(
            @RequestParam("userId") Long userId, @RequestParam("annualIncome") YearIncomeEnum annualIncome) {

        return userIncomeService.set(userId, annualIncome);
    }
}
