package io.github.seenings.trade.service;

import io.github.seenings.coin.enumeration.BusiType;

/**
 * TradeService
 *
 * @author chixuehui
 * @since 2023-03-11
 */
public interface TradeService {
    Long trade(Long inAccountId, Long outAccountId, Long coinAmount, BusiType busiType,
                  String description);

    Long sysInTrade(Long inSysAccountId, Long outAccountId, Long coinAmount, BusiType busiType, String description);

    Long sysOutTrade(Long inAccountId, Long outSysAccountId, Long coinAmount, BusiType busiType, String description);

}
