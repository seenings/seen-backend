package com.songchi.seen.work.http;

import com.songchi.seen.info.enumeration.YearIncomeEnum;
import com.songchi.seen.sys.constant.SeenConstant;
import com.songchi.seen.sys.constant.ServiceNameConstant;
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
    Map<Integer, Integer> userIdToAnnualIncome(@RequestBody Set<Integer> userIds);

    @PostMapping("set")
    boolean set(@RequestParam("userId") Integer userId, @RequestParam("annualIncome") YearIncomeEnum annualIncome);
}
