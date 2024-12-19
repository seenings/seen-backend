package com.songchi.seen.trade.service;

import com.songchi.seen.coin.enumeration.TradeType;

/**
 * CoinTradeTypeService
 *
 * @author chixuehui
 * @since 2023-03-05
 */
public interface CoinTradeTypeService {
    Integer addTradeType(Integer tradeId, TradeType tradeType);
}
