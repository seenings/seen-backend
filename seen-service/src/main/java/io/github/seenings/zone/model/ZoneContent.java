package io.github.seenings.zone.model;

import io.github.seenings.article.enumeration.ContentType;
import lombok.Data;

/**
 * 空间说说
 * @author chixuehui
 * @since 2022-05-03
 */
@Data
public class ZoneContent {

    /**
     * 内容类型
     */
    private ContentType contentType;

    /**
     * 文本
     */
    private String text;

    /**
     * 内容ID
     */
    private Integer contentId;
}
