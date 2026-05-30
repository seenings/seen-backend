package io.github.seenings.work.http;

import io.github.seenings.info.enumeration.YearIncomeEnum;
import io.github.seenings.sys.constant.SeenConstant;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.Map;
import java.util.Set;

/**
 * HttpUserIncomeService
 *
 * @author chixuehui
 * @since 2023-02-11
 */
@HttpExchange(
        value = SeenConstant.FEIGN_VERSION + "user/user-income")
public interface HttpUserIncomeService {
    @PostExchange("user-id-to-annual-income")
    Map<Long, Integer> userIdToAnnualIncome(@RequestBody Set<Long> userIds);

    @PostExchange("set")
    boolean set(@RequestParam("userId") Long userId, @RequestParam("annualIncome") YearIncomeEnum annualIncome);
}
