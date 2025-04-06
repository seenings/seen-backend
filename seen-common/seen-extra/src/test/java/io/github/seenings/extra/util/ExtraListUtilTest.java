package io.github.seenings.extra.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * ExtraListUtilTest
 *
 * @author chixuehui
 * @since 2022-11-27
 */
class ExtraListUtilTest {

    @ParameterizedTest
    @CsvSource({ //
        "8,4,2",
        "10,4,3",
        "1,4,1",
        "10,3,4",
    })
    void partition(int total, int size, int result) {
        List<Integer> collect = IntStream.range(0, total).boxed().collect(Collectors.toList());

        assertEquals(result, ExtraListUtil.partition(collect, size).size());
    }
    @ParameterizedTest
    @CsvSource({ //
        "8,1",
        "10,1",
        "1,1",
        "10,1",
        "203,3",
        "200,2",
    })
    void partition(int total, int result) {
        List<Integer> collect = IntStream.range(0, total).boxed().collect(Collectors.toList());

        assertEquals(result, ExtraListUtil.partition(collect).size());
    }
}
