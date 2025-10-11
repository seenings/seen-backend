package io.github.seenings.trade.service;

import io.github.seenings.coin.enumeration.BusiType;

/**
 * CoinTradeTypeService
 *
 * @author chixuehui
 * @since 2023-03-05
 */
public interface CoinTradeTypeService {
    Integer addTradeType(Integer tradeId, BusiType busiType);
}
