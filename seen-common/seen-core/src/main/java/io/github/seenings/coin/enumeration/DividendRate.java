package io.github.seenings.coin.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Objects;

/**
 * 分红比例
 */
@Getter
@AllArgsConstructor
public enum DividendRate {
    /**
     * 做任务分红
     */
    DO_TASK(1, "做任务分红", new BigDecimal("0.1")),
    /**
     * 充值分红
     */
    RECHARGE(2, "充值分红", new BigDecimal("0.01")),
    /**
     * 互动分红
     */
    INTERACTIVE(3, "互动分红", new BigDecimal("0.1")),
    /**
     * 发行分红,用于适度调节通货膨胀
     */
    ISSUE(4, "发行分红", new BigDecimal("0.1")),
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
    public static DividendRate indexToEnum(Integer index) {
        return Arrays.stream(values()).filter(n -> Objects.equals(index, n.index)).findFirst().orElse(null);
    }

}
