package io.github.seenings.work.service;

import io.github.seenings.info.enumeration.YearIncomeEnum;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * UserIncomeService
 *
 * @author chixuehui
 * @since 2023-02-11
 */
public interface UserIncomeService {
    Map<Long, Integer> userIdToAnnualIncome(Set<Long> userIds);

    long annualIncomeCount(YearIncomeEnum yearIncomeEnum);

    List<Long> annualIncomeToUserId(YearIncomeEnum yearIncomeEnum, int current, int size);

    boolean set(Long userId, YearIncomeEnum yearIncomeEnum);
}
