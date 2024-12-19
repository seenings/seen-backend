package com.songchi.seen.account.po;

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
 * @TableName coin_account
 */
@TableName(value = "coin_account")
@Data
@Accessors(chain = true)
public class CoinAccountPO implements Serializable {
    /**
     * 自增ID，账户ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 账户类型
     */
    private Integer accountType;

    /**
     * 账户描述，非结构化字段
     */
    private String description;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}