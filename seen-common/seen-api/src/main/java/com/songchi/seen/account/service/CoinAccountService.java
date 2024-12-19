package com.songchi.seen.account.service;

import com.songchi.seen.coin.enumeration.AccountType;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * CoinAccountService
 *
 * @author chixuehui
 * @since 2023-01-01
 */
public interface CoinAccountService {
    Map<Integer, List<Integer>> accountTypeToAccountId(Set<Integer> accountTypeIds);

    Map<Integer, AccountType> accountIdToAccountType(Set<Integer> accountIds);

    Integer createAccount(String description, AccountType accountType);
}
