package io.github.seenings.coin.po;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 币账户
 */

@Data
@Accessors(chain = true)
@TableName(value = "coin_account")
public class CoinAccount {
    /**
     * 自增ID，账户ID
     */
    private Long id;

    /**
     * 账户类型
     */
    private Integer accountType;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}
