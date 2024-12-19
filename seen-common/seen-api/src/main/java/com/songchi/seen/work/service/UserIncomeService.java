package com.songchi.seen.work.service;

import com.songchi.seen.info.enumeration.YearIncomeEnum;

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
    Map<Integer, Integer> userIdToAnnualIncome(Set<Integer> userIds);

    long annualIncomeCount(YearIncomeEnum yearIncomeEnum);

    List<Integer> annualIncomeToUserId(YearIncomeEnum yearIncomeEnum, int current, int size);

    boolean set(Integer userId, YearIncomeEnum yearIncomeEnum);
}
