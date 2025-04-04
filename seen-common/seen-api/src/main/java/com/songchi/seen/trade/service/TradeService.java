package com.songchi.seen.trade.service;

import com.songchi.seen.coin.enumeration.TradeType;

/**
 * TradeService
 *
 * @author chixuehui
 * @since 2023-03-11
 */
public interface TradeService {
    Integer trade(Long inAccountId, Long outAccountId, Integer coinAmount, TradeType tradeType,
                  String description);

    Integer sysInTrade(Long inSysAccountId, Long outAccountId, Integer coinAmount, TradeType tradeType, String description);

    Integer sysOutTrade(Long inAccountId, Long outSysAccountId, Integer coinAmount, TradeType tradeType, String description);

    Integer sysTrade(Long inSysAccountId, Long outSysAccountId, Integer coinAmount, TradeType tradeType, String description);
}
