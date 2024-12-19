package com.songchi.seen.zone.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 标签类型
 *
 */
@Getter
@AllArgsConstructor
public enum TagType {

    NONE(0, "无类型"),
    AGE(1, "年龄"),
    CHARACTER(2, "性格"),
    TALENT(3, "才艺"),
    GAME(4, "游戏"),
    CONSTELLATION(5, "星座"),
    ;

    /**
     * 索引
     */
    private final int index;
    /**
     * 标签
     */
    private final String label;
}
