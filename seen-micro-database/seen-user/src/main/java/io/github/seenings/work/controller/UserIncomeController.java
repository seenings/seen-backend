package io.github.seenings.work.controller;

import io.github.seenings.info.enumeration.YearIncomeEnum;
import io.github.seenings.work.http.HttpUserIncomeService;
import io.github.seenings.work.service.UserIncomeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
public class UserIncomeController implements HttpUserIncomeService {

    private UserIncomeService userIncomeService;

    @Override
    public Map<Long, Integer> userIdToAnnualIncome(@RequestBody Set<Long> userIds) {
        return userIncomeService.userIdToAnnualIncome(userIds);
    }

    @Override
    public boolean set(
            @RequestParam("userId") Long userId, @RequestParam("annualIncome") YearIncomeEnum annualIncome) {

        return userIncomeService.set(userId, annualIncome);
    }
}
