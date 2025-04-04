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
    Map<Long, Integer> accountIdCoinAmount(Set<Long> accountIds);

    boolean set(Long accountId, Integer amount);

    boolean add(Long accountId, Integer offsetAmount);
}
