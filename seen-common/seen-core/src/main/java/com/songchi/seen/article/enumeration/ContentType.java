package com.songchi.seen.article.enumeration;

import lombok.Getter;

/**
 * @author chixu
 * 2021/6/13 14:31
 */
@Getter
public enum ContentType {
    /**
     * 无类型
     */
    NONE(0, "无类型"),
    /**
     * 文本
     */
    TEXT(1, "文本"),
    /**
     * 图片
     */
    IMAGE(2, "图片"),
    /**
     * 语音
     */
    VOICE(3, "语音"),
    /**
     * 视频
     */
    VIDEO(4, "视频"),
    ;

    private final int index;
    private final String name;

    ContentType(int index, String name) {
        this.index = index;
        this.name = name;
    }
}
