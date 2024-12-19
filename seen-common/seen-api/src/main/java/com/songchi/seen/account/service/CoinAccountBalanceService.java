package com.songchi.seen.account.service;

import java.util.Map;
import java.util.Set;

/**
 * CoinAccountBalanceService
 *
 * @author chixuehui
 * @since 2023-01-01
 */
public interface CoinAccountBalanceService {
    Map<Integer, Integer> accountIdCoinAmount(Set<Integer> accountIds);

    boolean set(Integer accountId, Integer amount);

    boolean add(Integer accountId, Integer offsetAmount);
}
