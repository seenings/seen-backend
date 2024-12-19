package com.songchi.seen.introduce.util;

import com.songchi.seen.core.enumeration.ConstellationEnum;
import com.songchi.seen.introduce.enumeration.IntroduceTypeEnum;

import java.util.Arrays;
import java.util.Objects;

/**
 * IntroduceEnumUtils
 *
 * @author chixuehui
 * @since 2023-01-24
 */
public class IntroduceEnumUtils {

    /**
     * 根据索引号获取枚举
     *
     * @param index 索引号
     * @return 枚举对象
     */
    public static IntroduceTypeEnum indexToIntroduceTypeEnum(Integer index) {
        return Arrays.stream(IntroduceTypeEnum.values())
                .filter(n -> Objects.equals(n.getIndex(), index))
                .findFirst()
                .orElse(null);
    }

    /**
     * 根据索引获取枚举
     * @param index 索引
     * @return  枚举
     */
    public static ConstellationEnum indexToConstellationEnum(Integer index) {
        return Arrays.stream(ConstellationEnum.values())
                .filter(n -> Objects.equals(index, n.getIndex()))
                .findFirst()
                .orElse(null);
    }
}
