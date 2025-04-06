package io.github.seenings.work.http;

import io.github.seenings.info.enumeration.YearIncomeEnum;
import io.github.seenings.sys.constant.SeenConstant;
import io.github.seenings.sys.constant.ServiceNameConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.Set;

/**
 * HttpUserIncomeService
 *
 * @author chixuehui
 * @since 2023-02-11
 */
@FeignClient(
        name = ServiceNameConstant.SERVICE_SEEN_USER,
        contextId = "HttpUserIncomeService",
        path = SeenConstant.FEIGN_VERSION + "user/user-income")
public interface HttpUserIncomeService {
    @PostMapping("user-id-to-annual-income")
    Map<Long, Integer> userIdToAnnualIncome(@RequestBody Set<Long> userIds);

    @PostMapping("set")
    boolean set(@RequestParam("userId") Long userId, @RequestParam("annualIncome") YearIncomeEnum annualIncome);
}
