package io.github.seenings.sys.util;

import io.github.seenings.sys.core.CodeAndLabelEnum;
import io.github.seenings.sys.core.IndexAndLabelEnum;

import java.util.Arrays;
import java.util.Objects;

/**
 * 转枚举的工具类
 */
public class ToEnumerationUtil {
    /**
     * 根据索引获取枚举
     *
     * @param indexAndLabelEnumClass 枚举接口
     * @param index                  索引
     * @return 枚举
     */
    public static <T extends IndexAndLabelEnum> T indexToEnum(Class<T> indexAndLabelEnumClass, int index) {

        return Arrays.stream(indexAndLabelEnumClass.getEnumConstants()).filter(n -> n.getIndex() == index).findFirst().orElse(null);
    }

    /**
     * 根据代码获取枚举
     *
     * @param codeAndLabelEnumClass 枚举接口
     * @param code                  代码
     * @return 枚举
     */
    public static <T extends CodeAndLabelEnum> T codeToEnum(Class<T> codeAndLabelEnumClass, String code) {

        return Arrays.stream(codeAndLabelEnumClass.getEnumConstants()).filter(n -> Objects.equals(n.getCode(), code)).findFirst().orElse(null);
    }

    /**
     * 根据标签获取枚举
     *
     * @param indexAndLabelEnumClass 枚举接口
     * @param label                  标签
     * @return 枚举
     */
    public static <T extends IndexAndLabelEnum> T labelToEnumByIndex(Class<T> indexAndLabelEnumClass, String label) {

        return Arrays.stream(indexAndLabelEnumClass.getEnumConstants()).filter(n -> Objects.equals(n.getLabel(), label)).findFirst().orElse(null);
    }

    /**
     * 根据标签获取枚举
     *
     * @param codeAndLabelEnumClass 枚举接口
     * @param label                 标签
     * @return 枚举
     */
    public static <T extends CodeAndLabelEnum> T labelToEnum(Class<T> codeAndLabelEnumClass, String label) {

        return Arrays.stream(codeAndLabelEnumClass.getEnumConstants()).filter(n -> Objects.equals(n.getLabel(), label)).findFirst().orElse(null);
    }
}
