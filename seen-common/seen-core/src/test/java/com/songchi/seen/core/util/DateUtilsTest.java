package com.songchi.seen.core.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.songchi.seen.core.enumeration.ConstellationEnum;


/**
 * DateUtilsTest
 *
 * @author chixuehui
 * @since 2022-12-03
 */
class DateUtilsTest {

    @ParameterizedTest
    @CsvSource({ //
        "1,20,水瓶座",
        "2,20,双鱼座",
        "3,22,白羊座",
        "4,23,金牛座",
        "5,23,双子座",
        "6,23,巨蟹座",
        "7,23,狮子座",
        "8,23,处女座",
        "9,23,天秤座",
        "10,23,天秤座",
        "11,22,天蝎座",
        "11,23,射手座",
        "12,23,摩羯座",
    })
    void monthAndDayToConstellation(Integer month, Integer day, String result) {

        Assertions.assertEquals(result, DateUtils.monthAndDayToConstellation(month, day));
    }

    @Test
    void monthAndDayToConstellationEnum() {}

    @Test
    void constellationEnumToConstellation() {

        Assertions.assertEquals("白羊座", DateUtils.constellationEnumToConstellation(ConstellationEnum.ARIES));
    }

    @ParameterizedTest
    @CsvSource({
        "2,1,3,true",
        "1,2,3,false",
    })
    void between(int value, int min, int max, boolean result) {
        Assertions.assertEquals(result, DateUtils.between(value, min, max));
    }
}