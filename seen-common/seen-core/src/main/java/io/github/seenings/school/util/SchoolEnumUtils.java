package io.github.seenings.school.util;

import io.github.seenings.school.enumeration.Education;

import java.util.Arrays;
import java.util.Objects;

/**
 * SchoolEnumUtils
 *
 * @author chixuehui
 * @since 2023-02-11
 */
public class SchoolEnumUtils {

    /**
     * 根据索引获取枚举
     * @param index  索引
     * @return  枚举
     */
    public static Education indexToEducationEnum(Integer index) {
        return Arrays.stream(Education.values())
                .filter(n -> Objects.equals(n.getIndex(), index))
                .findFirst()
                .orElse(null);
    }
}
