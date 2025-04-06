package io.github.seenings.info.enumeration;

import lombok.Getter;

/**
 * 性别
 *
 * @author chixuehui
 * @since 2022-05-29
 */
@Getter
public enum Sex {
    NONE(0, "无"),

    MALE(1, "男"),
    FEMALE(2, "女"),
    ; //

    private final Integer index;
    private final String name;

    Sex(Integer index, String name) {
        this.index = index;
        this.name = name;
    }
}
