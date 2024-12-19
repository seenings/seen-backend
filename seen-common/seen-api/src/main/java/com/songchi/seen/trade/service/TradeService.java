package com.songchi.seen.trade.service;

import com.songchi.seen.coin.enumeration.TradeType;

/**
 * TradeService
 *
 * @author chixuehui
 * @since 2023-03-11
 */
public interface TradeService {
    Integer trade(Integer inAccountId, Integer outAccountId, Integer coinAmount, TradeType tradeType,
                  String description);

    Integer sysInTrade(Integer inSysAccountId, Integer outAccountId, Integer coinAmount, TradeType tradeType, String description);

    Integer sysOutTrade(Integer inAccountId, Integer outSysAccountId, Integer coinAmount, TradeType tradeType, String description);

    Integer sysTrade(Integer inSysAccountId, Integer outSysAccountId, Integer coinAmount, TradeType tradeType, String description);
}
