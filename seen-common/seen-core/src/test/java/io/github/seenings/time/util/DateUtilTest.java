package io.github.seenings.time.util;

import io.github.seenings.core.enumeration.ConstellationEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

class DateUtilTest {

    @ParameterizedTest
    @CsvSource({ //
            "1,20,水瓶座", "2,20,双鱼座", "3,22,白羊座", "4,23,金牛座", "5,23,双子座", "6,23,巨蟹座", "7,23,狮子座", "8,23,处女座", "9,23,天秤座", "10,23,天秤座", "11,22,天蝎座", "11,23,射手座", "12,23,摩羯座",})
    void monthAndDayToConstellation(Integer month, Integer day, String result) {

        Assertions.assertEquals(result, DateUtil.monthAndDayToConstellation(month, day));
    }


    @Test
    void constellationEnumToConstellation() {

        Assertions.assertEquals("白羊座", DateUtil.constellationEnumToConstellation(ConstellationEnum.ARIES));
    }

    @ParameterizedTest
    @CsvSource({"2,1,3,true", "1,2,3,false",})
    void between(int value, int min, int max, boolean result) {
        Assertions.assertEquals(result, DateUtil.between(value, min, max));
    }

    @Test
    void monthAndDayToConstellationEnum() {
    }

    @Test
    void between() {
    }

    @Test
    void monthAndDayToConstellation() {
    }


    @Test
    void basicIsoDateToLocalDate() {
    }

    @ParameterizedTest
    @CsvSource({//
            "1970,1,1,19700101"//
    })
    void localDateToBasicIsoDate(int year, int month, int dayOfMonth, String result) {
        Assertions.assertEquals(result, DateUtil.localDateToBasicIsoDate(LocalDate.of(year, month, dayOfMonth)));
    }

    @Test
    void localDateTimeToPatternFormatDate() {
    }

    @Test
    void localDateTimeToDate() {
    }

    @Test
    void localDateTimeToBasicIsoDate() {
    }

    @Test
    void basicIsoDateOffsetDays() {
    }
}
