package io.github.seenings.chat.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 申请状态
 */
@Getter
@AllArgsConstructor
public enum ApplyStatus {
    /**
     * 无
     */
    NONE(0, "无"),
    /**
     * 同意
     */
    AGREE(1, "同意"),
    /**
     * 拒绝
     */
    REFUSE(2, "拒绝"),
    /**
     * 过期
     */
    EXPIRE(3, "过期"),
    /**
     * 查看
     */
    LOOK(4, "查看"),
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
