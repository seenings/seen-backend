package io.github.seenings.common.util;

import static cn.hutool.core.collection.CollUtil.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.seenings.core.util.CollUtil;

/**
 * CollectionUtilsTest
 *
 * @author chixuehui
 * @since 2022-09-18
 */
class CollectionUtilsTest {

    @Test
    void isEmpty() {
        Assertions.assertTrue(CollUtil.isEmpty(newArrayList()));
    }

    @Test
    void isNotEmpty() {

        Assertions.assertTrue(CollUtil.isNotEmpty(newArrayList("")));
    }
}
