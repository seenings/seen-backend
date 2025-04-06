package io.github.seenings.coin.util;

import io.github.seenings.coin.enumeration.TradeType;

import java.util.Arrays;
import java.util.Objects;

/**
 * CoinEnumUtils
 *
 * @author chixuehui
 * @since 2023-02-19
 */
public class CoinEnumUtils {

    /**
     * 根据索引获取枚举
     * @param index 索引
     * @return  枚举
     */
    public static TradeType indexToTradeTypeEnum(Integer index) {
        return Arrays.stream(TradeType.values())
                .filter(n -> Objects.equals(index, n.getIndex()))
                .findFirst()
                .orElse(null);
    }
}
