package io.github.seenings.coin.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Objects;

/**
 * 佣金比例
 */
@Getter
@AllArgsConstructor
public enum CommissionRate {
    /**
     * 做任务佣金
     */
    DO_TASK(1, "做任务佣金", new BigDecimal("0.1")),
    /**
     * 充值佣金
     */
    RECHARGE(2, "充值佣金", new BigDecimal("0.01")),
    /**
     * 互动佣金
     */
    INTERACTIVE(3, "互动佣金", new BigDecimal("0.1")),
    ;
    /**
     * 索引
     */
    private final int index;
    /**
     * 标签
     */
    private final String label;
    /**
     * 比例值
     */
    private final BigDecimal rate;

    /**
     * 根据索引获取枚举
     *
     * @param index 索引
     * @return 枚举
     */
    public static CommissionRate indexToEnum(Integer index) {
        return Arrays.stream(values()).filter(n -> Objects.equals(index, n.index)).findFirst().orElse(null);
    }
}
