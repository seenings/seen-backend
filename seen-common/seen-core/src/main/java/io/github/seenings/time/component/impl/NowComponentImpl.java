package io.github.seenings.time.component.impl;

import io.github.seenings.time.util.DateUtil;
import io.github.seenings.time.component.NowComponent;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 当前时间组件
 */
public class NowComponentImpl implements NowComponent {

    /**
     * 当前时间转换为ISO-8601(yyyyMMdd)字符串格式日期
     *
     * @return ISO-8601(yyyyMMdd)字符串格式日期
     */
    @Override
    public String nowToBasicIsoDate() {

        return DateUtil.localDateToBasicIsoDate(LocalDate.now());
    }

    /**
     * 当前时间转十七位格式日期时间
     *
     * @return 十七位格式日期时间
     */
    @Override
    public String nowToSeventeenFormatDate() {

        return DateUtil.localDateTimeToPatternFormatDate(LocalDateTime.now(), DateUtil.SEVENTEEN_FORMAT);
    }

}
