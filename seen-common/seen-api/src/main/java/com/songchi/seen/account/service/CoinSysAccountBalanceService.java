package com.songchi.seen.account.service;

import java.util.Map;
import java.util.Set;

/**
 * CoinSysAccountBalanceService
 *
 * @author chixuehui
 * @since 2023-03-05
 */
public interface CoinSysAccountBalanceService {
    Map<Integer, Integer> accountIdCoinAmount(Set<Integer> accountIds);

    boolean add(Integer accountId, Integer offsetAmount);
}
