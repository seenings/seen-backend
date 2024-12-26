package com.songchi.seen.trade.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 用户充值
 *
 */
@TableName(value = "coin_transfer")
@Data
public class CoinTransferPO {
    /**
     * 自增ID，充值单ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 渠道类型
     */
    private Integer channelType;

    /**
     * 渠道账号
     */
    private String channelAccount;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 充值金额
     */
    private Integer money;

    /**
     * 充值时间
     */
    private Date tradeTime;
    /**
     * 交易ID
     */
    private Integer tradeId;
    /**
     * 创建时间
     */
    private Date createTime;
}
