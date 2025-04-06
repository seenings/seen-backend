package io.github.seenings.common.model;

import lombok.Getter;

/**
 * @author chixh
 * 2021-05-08
 */
@Getter
public enum StatusCode {

    ERROR(0, "失败"),

    SUCCESS(1, "成功"),//
    ;
    private final int code;

    private final String msg;

    StatusCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
