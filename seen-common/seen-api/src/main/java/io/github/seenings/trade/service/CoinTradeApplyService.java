package io.github.seenings.trade.service;

import java.util.Set;

/**
 * CoinTradeApplyService
 *
 * @author chixuehui
 * @since 2023-01-01
 */
public interface CoinTradeApplyService {
    void set(Set<Integer> tradeIds, Integer applyId);
}
