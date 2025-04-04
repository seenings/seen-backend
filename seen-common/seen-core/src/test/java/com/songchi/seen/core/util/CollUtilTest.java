package com.songchi.seen.core.util;

import static cn.hutool.core.collection.CollUtil.*;

import java.util.HashSet;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * CollUtilsTest
 *
 * @author chixuehui
 * @since 2022-12-03
 */
class CollUtilTest {

    @Test
    void first() {
        HashSet<Integer> objects = new HashSet<>();
        objects.add(1);
        objects.add(2);
        Assertions.assertEquals(1, CollUtil.first(objects));
    }

    @Test
    void contains() {

        Assertions.assertFalse(CollUtil.contains(newArrayList(), 1));
        Assertions.assertFalse(CollUtil.contains(null, 1));
        Assertions.assertTrue(CollUtil.contains(newArrayList(1), 1));
    }

    @Test
    void size() {

        Assertions.assertEquals(1, CollUtil.size(newArrayList(1)));
        Assertions.assertEquals(0, CollUtil.size(newHashSet()));
    }
}
