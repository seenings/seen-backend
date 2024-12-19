package com.songchi.seen.core.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * NumberUtilsTest
 *
 * @author chixuehui
 * @since 2022-12-03
 */
class NumberUtilsTest {

    @ParameterizedTest
    @CsvSource({ //
        ",",
        "1,1",
    })
    void intToString(Integer value, String result) {
        Assertions.assertEquals(result, NumberUtils.intToString(value));
    }

    @ParameterizedTest
    @CsvSource({ //
        ",0",
        "0,0",
        "1,1",
    })
    void defaultIfNull(Integer value, Integer result) {
        Assertions.assertEquals(result, NumberUtils.defaultIfNull(value, 0));
    }
}