package com.songchi.seen.work.controller;

import com.songchi.seen.info.enumeration.YearIncomeEnum;
import com.songchi.seen.work.http.HttpUserIncomeService;
import com.songchi.seen.work.service.UserIncomeService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping(FEIGN_VERSION + "user/user-income")
public class UserIncomeController implements HttpUserIncomeService {

    @Resource
    private UserIncomeService userIncomeService;

    @Override
    @PostMapping("user-id-to-annual-income")
    public Map<Integer, Integer> userIdToAnnualIncome(@RequestBody Set<Integer> userIds) {
        return userIncomeService.userIdToAnnualIncome(userIds);
    }

    @Override
    @PostMapping("set")
    public boolean set(
            @RequestParam("userId") Integer userId, @RequestParam("annualIncome") YearIncomeEnum annualIncome) {

        return userIncomeService.set(userId, annualIncome);
    }
}
