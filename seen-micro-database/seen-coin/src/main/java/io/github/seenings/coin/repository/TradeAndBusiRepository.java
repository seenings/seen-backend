package io.github.seenings.coin.repository;

import java.time.LocalDateTime;

/**
 * 交易与业务关系
 */
public interface TradeAndBusiRepository {
    /**
     * 增加交易与业务关系
     * @param tradeTime 交易时间
     * @param busiId    业务ID
     * @param tradeId   交易ID
     * @return  是否增加成功
     */
    boolean add(LocalDateTime tradeTime, Long busiId, Long tradeId);
}
