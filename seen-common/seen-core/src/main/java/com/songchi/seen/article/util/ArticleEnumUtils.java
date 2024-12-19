package com.songchi.seen.article.util;

import com.songchi.seen.article.enumeration.ContentType;

import java.util.Arrays;
import java.util.Objects;

/**
 * ArticleEnumUtils
 */
public class ArticleEnumUtils {

    /**
     * 根据编码获取枚举
     *
     * @param index 编码
     * @return 枚举
     */
    public static ContentType indexToContentTypeEnum(Integer index) {
        return Arrays.stream(ContentType.values()).filter(n -> Objects.equals(n.getIndex(), index)).findFirst().orElse(null);
    }
}
