package com.songchi.seen.extra.util;

import cn.hutool.core.collection.ListUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * ExtraListUtil
 *
 * @author chixuehui
 * @since 2022-11-27
 */
public interface ExtraListUtil {

    /**
     * 分裂
     * @param collections   集合
     * @param size  大小
     * @return  多个集合
     * @param <E>   元素类型
     */
    public static <E> List<List<E>> partition(Collection<E> collections, int size) {

        return ListUtil.partition(new ArrayList<>(collections), size);
    }
    /**
     * 分裂，默认1组100个
     * @param collections   集合
     * @return  多个集合
     * @param <E>   元素类型
     */
    public static <E> List<List<E>> partition(Collection<E> collections) {

        return partition(collections, 100);
    }
}
