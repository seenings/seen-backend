package com.songchi.seen.coin.enumeration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.CsvSources;

import static org.junit.jupiter.api.Assertions.*;

class AccountTypeTest {

    @ParameterizedTest
    @CsvSource({//
            "401,用户",//
    })
    void indexToEnum(int index, String expected) {
        AccountType accountType = AccountType.indexToEnum(index);
        Assertions.assertEquals(expected, accountType.getLabel());
    }
}
