package io.github.seenings.core.util;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * MapUtils
 *
 * @author chixuehui
 * @since 2022-12-03
 */
public class MapUtils {

    /**
     * 多个value展开成集
     * @param map   map
     * @return  集
     * @param <K>   键类型
     * @param <V>   值的集合元素类型
     */
    public static <K, V> Set<V> valueSetToSet(Map<K, Set<V>> map) {
        return map.values().stream().parallel().flatMap(Collection::stream).collect(Collectors.toSet());
    }
    /**
     * 多个value展开成集
     * @param map   map
     * @return  集
     * @param <K>   键类型
     * @param <V>   值的集合元素类型
     */
    public static <K, V> Set<V> valueListToSet(Map<K, List<V>> map) {
        return map.values().stream().parallel().flatMap(Collection::stream).collect(Collectors.toSet());
    }
}
