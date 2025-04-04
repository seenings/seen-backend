package io.github.seenings.coin.enumeration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class DividendRateTest {
    @ParameterizedTest
    @CsvSource({//
            "1,做任务分红",//
    })
    void indexToEnum(int index, String expected) {
        DividendRate dividendRate = DividendRate.indexToEnum(index);
        Assertions.assertEquals(expected, dividendRate.getLabel());
    }
}
