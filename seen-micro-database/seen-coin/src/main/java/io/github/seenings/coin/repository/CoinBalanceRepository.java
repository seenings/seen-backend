package io.github.seenings.coin.repository;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

/**
 * 玫瑰币余额
 */
public interface CoinBalanceRepository {
    /**
     * 更新余额
     *
     * @param amount          数量
     * @param transactionTime 成交时间
     * @param debitOrCreditId 借方或者贷方ID
     * @return 是否更新成功
     */
    boolean update(Long amount, LocalDateTime transactionTime, Long debitOrCreditId);

    /**
     * 根据账户ID获取余额
     *
     * @param debitOrCreditIds 账户ID
     * @return 账户ID对应余额
     */
    Map<Long, Long> debitOrCreditIdToBalance(Set<Long> debitOrCreditIds);
}
