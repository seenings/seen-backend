package io.github.seenings.file.util;

import io.github.seenings.file.enumeration.StorageType;
import io.github.seenings.sys.util.ToEnumerationUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * FileIndexToEnumerationUtilTest
 *
 * @author chixuehui
 * @since 2024-09-22
 */
class FileIndexToEnumerationUtilTest {

    @Test
    void indexToStorageTypeEnum() {

        Assertions.assertEquals("本地存储", ToEnumerationUtil.indexToEnum(StorageType.class, 1).getLabel());
    }

    @ParameterizedTest
    @CsvSource({//
            "1,本地存储",//
            "3,阿里云对象存储"//
    })
    void indexToStorageTypeEnum(int index, String expected) {

        Assertions.assertEquals(expected, ToEnumerationUtil.indexToEnum(StorageType.class, index).getLabel());
    }

}
