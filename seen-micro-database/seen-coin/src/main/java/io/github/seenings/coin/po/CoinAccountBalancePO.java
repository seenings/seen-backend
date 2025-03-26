package io.github.seenings.coin.po;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 币账户余额
 *
 */
@TableName(value = "coin_account_balance")
@Data
@Accessors(chain = true)
public class CoinAccountBalancePO {
    /**
     * 自增ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 账户ID
     */
    private Integer accountId;

    /**
     * 账户余额
     */
    private Integer coinAmount;

    /**
     * 余额变动时间
     */
    private LocalDateTime changeTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
