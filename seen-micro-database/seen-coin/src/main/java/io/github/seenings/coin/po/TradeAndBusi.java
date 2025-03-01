package io.github.seenings.coin.po;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 交易与业务关系
 */
@Data
@Accessors(chain = true)
public class TradeAndBusi {


    /**
     * 交易ID
     */
    private Long tradeId;


    /**
     * 业务ID
     */
    private Long busiId;

    /**
     * 交易时间
     */
    private LocalDateTime tradeTime;
}
