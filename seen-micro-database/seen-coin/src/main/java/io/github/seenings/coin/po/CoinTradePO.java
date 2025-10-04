package io.github.seenings.coin.po;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 币交易流水
 *
 */
@TableName(value = "coin_trade")
@Data
@Accessors(chain = true)
public class CoinTradePO {
    /**
     * 自增ID，币流水ID
     */
    private Integer id;

    /**
     * 进入账户ID，增加
     */
    private Long inAccountId;

    /**
     * 出去账户ID，减少
     */
    private Long outAccountId;

    /**
     * 币个数
     */
    private Integer coinAmount;

    /**
     * 交易描述，非结构化字段
     */
    private String description;

    /**
     * 交易时间
     */
    private LocalDateTime tradeTime;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
