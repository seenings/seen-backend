package com.songchi.seen.trade.service;

/**
 * CoinTradeService
 *
 * @author chixuehui
 * @since 2023-01-01
 */
public interface CoinTradeService {
    Integer addTrade(Integer inAccountId, Integer outAccountId, Integer coinAmount, String description);
}
