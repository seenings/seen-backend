package io.github.seenings.coin.enumeration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CommissionRateTest {

    @ParameterizedTest
    @CsvSource({//
            "1,做任务佣金",//
    })
    void indexToEnum(int index, String expected) {
        CommissionRate commissionRate = CommissionRate.indexToEnum(index);
        Assertions.assertEquals(expected, commissionRate.getLabel());
    }
}
