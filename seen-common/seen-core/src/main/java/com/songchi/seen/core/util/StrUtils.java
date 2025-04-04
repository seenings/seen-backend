package com.songchi.seen.core.util;

/**
 * StrUtils
 *
 * @author chixuehui
 * @since 2022-12-03
 */
public class StrUtils {
    public static Integer stringToInt(String value) {
        if (value == null) {
            return null;
        }
        return Integer.valueOf(value);
    }

    /**
     * 字符串转数字
     *
     * @param value 值
     * @return 数字
     */
    public static Long stringToLong(String value) {
        if (value == null) {
            return null;
        }
        return Long.valueOf(value);
    }

    /**
     * 对象转字符串
     *
     * @param value 值
     * @return 字符串
     */
    public static String objectToString(Object value) {
        if (value == null) {
            return null;
        }
        return String.valueOf(value);
    }
}
