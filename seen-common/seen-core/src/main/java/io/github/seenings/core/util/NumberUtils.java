package io.github.seenings.core.util;

/**
 * NumberUtils
 *
 * @author chixuehui
 * @since 2022-12-03
 */
public class NumberUtils {
    public static String intToString(Integer value) {
        if (value == null) {
            return null;
        }
        return String.valueOf(value);
    }

    /**
     * 如果整数是null，用默认值替代
     * @param value 整数
     * @param defaultValue  默认值
     * @return  返回结果
     */
    public static Integer defaultIfNull(Integer value, Integer defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        return value;
    }
}
