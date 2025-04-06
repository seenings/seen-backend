package io.github.seenings.info.enumeration;

import lombok.Getter;

/**
 * UserAuth
 *
 * @author chixuehui
 * @since 2022-10-16
 */
@Getter
public enum UserAuthEnum {
    NO_AUTH(0, "未认证"),
    HAVE_AUTH(1, "已认证"),
    ;

    private final int index;
    private final String name;

    UserAuthEnum(int index, String name) {
        this.index = index;
        this.name = name;
    }
}
