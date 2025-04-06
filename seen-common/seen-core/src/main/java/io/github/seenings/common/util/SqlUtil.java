package io.github.seenings.common.util;

/**
 * SqlUtil
 *
 * @author chixuehui
 * @since 2022-05-04
 */
public class SqlUtil {

    /**
     * 增加count
     *
     * @param columnName 列名
     * @return count语句
     */
    public static String count(String columnName) {

        return "count(" + columnName + ") as count";
    }
}
