package io.github.seenings.core.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * StrUtilsTest
 *
 * @author chixuehui
 * @since 2022-12-03
 */
class StrUtilsTest {

    @ParameterizedTest
    @CsvSource({//
            ",",//
            "1,1",//
            "2,2",//
    })
    void stringToInt(String value, Integer result) {
        Assertions.assertEquals(result,StrUtils.stringToInt(value));
    }
}
