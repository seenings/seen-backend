package io.github.seenings.coin.po;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 玫瑰币余额
 * 借方/贷方ID 余额 成交时间
 */
@Data
@Accessors(chain = true)
public class CoinBalance {
    /**
     * 借方/贷方ID
     */
    private Long debitOrCreditId;
    /**
     * 余额
     */
    private Long balance;
    /**
     * 成交时间
     */
    private LocalDateTime transactionTime;
}
