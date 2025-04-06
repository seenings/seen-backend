package io.github.seenings.coin.po;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 玫瑰币记账
 * 交易ID 数量 借方 贷方 成交时间
 */
@Data
@Accessors(chain = true)
public class CoinBook {
    /**
     * 交易ID
     */
    private Long tradeId;
    /**
     * 数量
     */
    private Long amount;
    /**
     * 借方
     */
    private Long debitId;
    /**
     * 贷方
     */
    private Long creditId;
    /**
     * 成交时间
     */
    private LocalDateTime transactionTime;
}
