package com.songchi.seen.zone.util;

import java.util.Arrays;

import com.songchi.seen.zone.model.TagType;

/**
 * 枚举工具类
 */
public class TagIntEnumUtil {

    /**
     * 根据索引获取枚举
     * 
     * @param index 索引
     * @return 枚举
     */
    public static TagType indexToTagTypeEnum(int index) {
        return Arrays.stream(TagType.values())
                .filter(n -> n.getIndex() == index).findFirst().orElse(null);

    }
}
