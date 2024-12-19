package com.songchi.seen.common.util;

import static cn.hutool.core.collection.CollUtil.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.songchi.seen.core.util.CollUtils;

/**
 * CollectionUtilsTest
 *
 * @author chixuehui
 * @since 2022-09-18
 */
class CollectionUtilsTest {

    @Test
    void isEmpty() {
        Assertions.assertTrue(CollUtils.isEmpty(newArrayList()));
    }

    @Test
    void isNotEmpty() {

        Assertions.assertTrue(CollUtils.isNotEmpty(newArrayList("")));
    }
}