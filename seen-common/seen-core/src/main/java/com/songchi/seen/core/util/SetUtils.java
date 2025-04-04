package com.songchi.seen.core.util;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * SetUtils
 *
 * @author chixuehui
 * @since 2022-10-23
 */
public class SetUtils {

    /**
     * 差集，在第一个列表中，但不在第二个列表中
     * @param firstSet 第一个列表
     * @param secondSet    第二个列表
     * @return  列表
     * @param <T>   元素类型
     */
    public static <T> Set<T> except(Set<T> firstSet, Set<T> secondSet) {
        if (CollUtil.isEmpty(firstSet)) {
            return Collections.emptySet();
        }
        if (CollUtil.isEmpty(secondSet)) {
            return new HashSet<>(firstSet);
        }
        return firstSet.stream().parallel().filter(n -> !secondSet.contains(n)).collect(Collectors.toSet());
    }
    /**
     * 并集，相同元素去重
     * @param firstSet 第一个列表
     * @param secondSet    第二个列表
     * @return  列表
     * @param <T>   元素类型
     */
    public static <T> Set<T> union(Collection<T> firstSet, Collection<T> secondSet) {
        if (CollUtil.isEmpty(firstSet)) {
            return new HashSet<>(secondSet);
        }
        if (CollUtil.isEmpty(secondSet)) {
            return new HashSet<>(firstSet);
        }
        return Stream.of(firstSet, secondSet)
                .parallel()
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }
    /**
     * 交集，获取相同元素
     * @param firstSet 第一个列表
     * @param secondSet    第二个列表
     * @return  列表
     * @param <T>   元素类型
     */
    public static <T> Set<T> interset(Set<T> firstSet, Set<T> secondSet) {
        if (CollUtil.isEmpty(firstSet)) {
            return Collections.emptySet();
        }
        if (CollUtil.isEmpty(secondSet)) {
            return Collections.emptySet();
        }
        return firstSet.stream().parallel().filter(secondSet::contains).collect(Collectors.toSet());
    }
}
