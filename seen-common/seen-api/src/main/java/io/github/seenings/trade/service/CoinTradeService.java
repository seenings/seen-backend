package io.github.seenings.trade.service;

/**
 * CoinTradeService
 *
 * @author chixuehui
 * @since 2023-01-01
 */
public interface CoinTradeService {
    Integer addTrade(Long inAccountId, Long outAccountId, Integer coinAmount, String description);
}
