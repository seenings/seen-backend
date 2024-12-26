package com.songchi.seen.trade.po;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 交易类型
 *
 */
@TableName(value = "coin_trade_type")
@Data
@Accessors(chain = true)
public class CoinTradeTypePO {
    /**
     * 自增ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 交易ID，对应coin_trade
     */
    private Integer tradeId;

    /**
     * 交易类型ID
     */
    private Integer tradeTypeId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
