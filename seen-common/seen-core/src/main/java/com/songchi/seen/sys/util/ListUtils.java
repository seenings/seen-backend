package com.songchi.seen.sys.util;

import com.songchi.seen.core.util.CollUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 数组工具类
 */
public class ListUtils {

    /**
     * 过滤值为null的情况
     *
     * @param set 集
     * @param <T> 值类型
     * @return 列表
     */
    public static <T> List<T> valueIsNull(Set<T> set) {
        if (CollUtils.isEmpty(set)) {
            return Collections.emptyList();
        }
        return set.stream().filter(Objects::nonNull).collect(Collectors.toList());
    }
    /**
     * 差集，在第一个列表中，但不在第二个列表中
     * @param firstList 第一个列表
     * @param secondList    第二个列表
     * @return  列表
     * @param <T>   元素类型
     */
    public static <T> List<T> except(List<T> firstList, List<T> secondList) {
        if (CollUtils.isEmpty(firstList)) {
            return Collections.emptyList();
        }
        if (CollUtils.isEmpty(secondList)) {
            return new ArrayList<>(firstList);
        }
        return firstList.stream()
                .parallel()
                .filter(n -> !secondList.contains(n))
                .collect(Collectors.toList());
    }
    /**
     * 并集，相同元素去重
     * @param firstList 第一个列表
     * @param secondList    第二个列表
     * @return  列表
     * @param <T>   元素类型
     */
    public static <T> List<T> union(List<T> firstList, List<T> secondList) {
        if (CollUtils.isEmpty(firstList)) {
            return new ArrayList<>(secondList);
        }
        if (CollUtils.isEmpty(secondList)) {
            return new ArrayList<>(firstList);
        }
        return Stream.of(firstList, secondList)
                .parallel()
                .flatMap(Collection::stream)
                .distinct()
                .collect(Collectors.toList());
    }
    /**
     * 交集，获取相同元素
     * @param firstList 第一个列表
     * @param secondList    第二个列表
     * @return  列表
     * @param <T>   元素类型
     */
    public static <T> List<T> interset(List<T> firstList, List<T> secondList) {
        if (CollUtils.isEmpty(firstList)) {
            return Collections.emptyList();
        }
        if (CollUtils.isEmpty(secondList)) {
            return Collections.emptyList();
        }
        return firstList.stream().parallel().filter(secondList::contains).collect(Collectors.toList());
    }

    public static <T> List<T> headSimple(List<T> firstList, List<T> secondList) {
        if (CollUtils.isEmpty(firstList)) {
            return Collections.emptyList();
        }
        if (CollUtils.isEmpty(secondList)) {
            return Collections.emptyList();
        }
        int minSize = Math.min(firstList.size(), secondList.size());
        List<T> result = new ArrayList<>();
        for (int i = 0; i < minSize; i++) {
            T first = firstList.get(i);
            T second = secondList.get(i);
            if (Objects.equals(first, second)) {
                result.add(first);
            } else {
                break;
            }
        }
        return result;
    }
}
