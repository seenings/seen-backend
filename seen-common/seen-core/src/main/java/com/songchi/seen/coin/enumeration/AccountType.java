package com.songchi.seen.coin.enumeration;

import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * AccountType，预留99种系统账户
 */
@Getter
public enum AccountType {

    /**
     * 1. 系统充值账户，虚拟币来源
     * 2. 系统活动奖励账户，虚拟币来源
     * 3. 系统临时过期账户，接收过期
     * 4. 系统使用账户，接收使用
     */
    SYS_RECHARGE(1, "系统充值账户"),
    SYS_REWARD(2, "系统活动奖励账户"),
    SYS_EXPIRE(3, "系统过期账户"),
    SYS_USE(4, "系统使用账户"),

    /**
     * 100. 永久账户，充值+推荐进永久
     * 101. 临时账户，其他进临时
     * 102. 用户冻结账户
     */
    USER_FOREVER(101, "用户永久账户"),
    USER_TEMPORARY(102, "用户临时账户"),
    USER_FREEZE(103, "用户冻结账户"),

    /**
     * 玫瑰币基础账户
     * 现金基础账户
     * 系统佣金账户
     * 系统分红账户
     * 用户
     */
    COIN_BASIC(301, "玫瑰币基础账户"),
    CASH_BASIC(302, "现金基础账户"),
    SYS_COMMISSION(303, "系统佣金账户"),
    SYS_DIVIDEND(304, "系统分红账户"),
    USER(401, "用户"),
    ;
    private final int index;
    private final String label;

    AccountType(int index, String label) {
        this.index = index;
        this.label = label;
    }

    /**
     * 根据索引获取枚举
     *
     * @param index 索引
     * @return 枚举
     */
    public static AccountType indexToEnum(Integer index) {
        return Arrays.stream(values())
                .filter(n -> Objects.equals(index, n.index))
                .findFirst()
                .orElse(null);
    }
}
