package com.songchi.seen.info.enumeration;

import lombok.Getter;

/**
 * 婚姻状况
 *
 * @author chixuehui
 * @since 2023-02-05
 */
@Getter
public enum MaritalStatus {
    MARRIED(0, "已婚"),
    UNMARRIED(1, "未婚"),
    DIVORCED_WITHOUT_CHILDREN(2, "离异无孩"),
    DIVORCED_WITH_CHILDREN_LOOK(3, "离异带孩"),
    DIVORCED_WITH_CHILDREN_NOT_LOOK(4, "离异不带孩"),
    BEREAVEMENT(5, "丧偶"),
    ; //

    private final Integer index;
    private final String name;

    MaritalStatus(Integer index, String name) {
        this.index = index;
        this.name = name;
    }
}
