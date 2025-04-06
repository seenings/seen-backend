package io.github.seenings.info.util;

import io.github.seenings.info.enumeration.MaritalStatus;
import io.github.seenings.info.enumeration.Sex;
import io.github.seenings.info.enumeration.UserAuthEnum;
import io.github.seenings.info.enumeration.YearIncomeEnum;

import java.util.Arrays;
import java.util.Objects;

/**
 * UserEnumUtils
 *
 * @author chixuehui
 * @since 2023-01-24
 */
public class UserEnumUtils {

    /**
     * 根据索引号获取枚举
     *
     * @param index 索引号
     * @return 枚举对象
     */
    public static Sex indexToSexEnum(Integer index) {
        return Arrays.stream(Sex.values())
                .filter(n -> Objects.equals(n.getIndex(), index))
                .findFirst()
                .orElse(null);
    }
    /**
     * 根据索引号获取枚举
     *
     * @param index 索引号
     * @return 枚举对象
     */
    public static MaritalStatus indexToMaritalStatus(Integer index) {
        return Arrays.stream(MaritalStatus.values())
                .filter(n -> Objects.equals(n.getIndex(), index))
                .findFirst()
                .orElse(null);
    }

    /**
     * 根据索引号获取枚举
     *
     * @param index 索引号
     * @return 枚举对象
     */
    public static YearIncomeEnum indexToYearIncomeEnum(Integer index) {
        return Arrays.stream(YearIncomeEnum.values())
                .filter(n -> Objects.equals(n.getIndex(), index))
                .findFirst()
                .orElse(null);
    }

    /**
     * 根据index获取枚举
     * @param index index
     * @return  枚举
     */
    public static UserAuthEnum indexToUserAuthEnum(Integer index) {
        return Arrays.stream(UserAuthEnum.values())
                .filter(n -> Objects.equals(n.getIndex(), index))
                .findFirst()
                .orElse(null);
    }
}
