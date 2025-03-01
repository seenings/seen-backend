package io.github.seenings.coin.repository;

import java.time.LocalDateTime;

/**
 * 玫瑰币记账
 */
public interface CoinBookRepository {
    /**
     * 增加
     *
     * @param amount          数量
     * @param debitId         借方
     * @param creditId        贷方
     * @param transactionTime 成交时间
     * @return 交易ID
     */
    Long add(Long amount, Long debitId, Long creditId, LocalDateTime transactionTime);
}
