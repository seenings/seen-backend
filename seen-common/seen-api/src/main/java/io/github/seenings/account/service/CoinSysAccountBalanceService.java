package io.github.seenings.account.service;

import java.util.Map;
import java.util.Set;

/**
 * CoinSysAccountBalanceService
 *
 * @author chixuehui
 * @since 2023-03-05
 */
public interface CoinSysAccountBalanceService {
    Map<Long, Integer> accountIdCoinAmount(Set<Long> accountIds);

    boolean add(Long accountId, Integer offsetAmount);
}
