package io.github.seenings.core.util;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 集合工具类
 *
 * @author chixuehui
 * @since 2022-10-07
 */
public class CollUtil {

    /**
     * 包含
     * @param collection    集
     * @param value 值
     * @return  是否包含
     * @param <T>   类型
     */
    public static <T> boolean contains(Collection<T> collection, T value) {
        if (isEmpty(collection)) {
            return false;
        }
        return collection.contains(value);
    }
    /**
     * 集合去空
     *
     * @param collection 集合
     * @param <T>        类型
     * @return 列表
     */
    public static <T> List<T> valueIsNullToList(Collection<T> collection) {
        if (collection == null) {
            return null;
        }
        return collection.stream().filter(Objects::nonNull).collect(Collectors.toList());
    }

    /**
     * 集合去空
     *
     * @param collection 集合
     * @param <T>        类型
     * @return 集
     */
    public static <T> Set<T> valueIsNullToSet(Collection<T> collection) {
        return collection.stream().filter(Objects::nonNull).collect(Collectors.toSet());
    }

    /**
     * 是否是空集合
     * @param collection    集合
     * @return  是否是空
     * @param <T>   类型
     */
    public static <T> boolean isEmpty(Collection<T> collection) {
        return collection == null || collection.isEmpty();
    }
    /**
     * 是否不是空集合
     * @param collection    集合
     * @return  是否不是空
     * @param <T>   类型
     */
    public static <T> boolean isNotEmpty(Collection<T> collection) {
        return !isEmpty(collection);
    }

    /**
     * 获取第一个
     * @param collection    集合
     * @return  第一个元素
     * @param <T>   元素类型
     */
    public static <T> T first(Collection<T> collection) {
        if (isEmpty(collection)) {
            return null;
        }
        return collection.stream().findFirst().orElse(null);
    }

    /**
     * 集合大小
     * @param collection    集合
     * @return  集合大小
     * @param <T>   元素类型
     */
    public static <T> int size(Collection<T> collection) {
        if (isEmpty(collection)) {
            return 0;
        } else {
            return collection.size();
        }
    }
}
