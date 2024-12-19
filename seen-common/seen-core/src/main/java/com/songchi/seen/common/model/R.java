package com.songchi.seen.common.model;

import lombok.Data;

/**
 * @author chixh
 * 2021-05-08
 */
@Data
public class R<T> {

    /**
     * 状态码
     */
    private int code;
    /**
     * 消息
     */
    private String msg;
    /**
     * 数据
     */
    private T data;
}
