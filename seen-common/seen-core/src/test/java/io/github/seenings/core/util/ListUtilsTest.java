package io.github.seenings.core.util;

import cn.hutool.core.collection.CollUtil;
import io.github.seenings.sys.util.ListUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * ListUtilsTest
 *
 * @author chixuehui
 * @since 2023-02-12
 */
class ListUtilsTest {

    @Test
    void headSimple() {

        Assertions.assertEquals(
                CollUtil.newArrayList(1),
                ListUtils.headSimple(CollUtil.newArrayList(1, 3, 2), CollUtil.newArrayList(1, 2, 3)));
        Assertions.assertEquals(
                CollUtil.newArrayList(1, 2),
                ListUtils.headSimple(CollUtil.newArrayList(1, 2, 4), CollUtil.newArrayList(1, 2, 3)));
        Assertions.assertEquals(
                CollUtil.newArrayList(),
                ListUtils.headSimple(CollUtil.newArrayList(1, 3, 2), CollUtil.newArrayList(4, 2, 3)));
        Assertions.assertEquals(
                CollUtil.newArrayList(), ListUtils.headSimple(CollUtil.newArrayList(), CollUtil.newArrayList(4, 2, 3)));
        Assertions.assertEquals(
                CollUtil.newArrayList(), ListUtils.headSimple(CollUtil.newArrayList(1, 2, 3), CollUtil.newArrayList()));
        Assertions.assertEquals(
                CollUtil.newArrayList(),
                ListUtils.headSimple(CollUtil.newArrayList(1, 2, 3), CollUtil.newArrayList(4, 5, 6)));
    }
}
