package io.github.seenings.info.enumeration;

import lombok.Getter;

/**
 * YearIncomeEnum
 *
 * @author chixuehui
 * @since 2023-02-05
 */
@Getter
public enum YearIncomeEnum {
    YEAR_MIN_TO_5_W(0, "小于5W"),
    YEAR_5_TO_15_W(1, "5-15W"),
    YEAR_15_TO_30_W(2, "15-30W"),
    YEAR_30_TO_50_W(3, "30-50W"),
    YEAR_50_TO_100_W(4, "50-100W"),
    YEAR_100_TO_500_W(5, "100-500W"),
    YEAR_500_TO_MAX_W(6, "大于500W"),
    ; //

    private final Integer index;
    private final String name;

    YearIncomeEnum(Integer index, String name) {
        this.index = index;
        this.name = name;
    }
}
