package io.github.seenings.account.service;

import io.github.seenings.coin.enumeration.AccountType;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 币账户
 */
public interface CoinAccountService {
    Map<Integer, List<Long>> accountTypeToAccountId(Set<Integer> accountTypeIds);

    Map<Long, AccountType> accountIdToAccountType(Set<Long> accountIds);

    /**
     * 创建账户
     *
     * @param accountType 账户类型
     * @return 账户号
     */
    Long createAccount(AccountType accountType);
}
