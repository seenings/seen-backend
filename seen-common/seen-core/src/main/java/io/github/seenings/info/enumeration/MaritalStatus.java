package io.github.seenings.info.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 婚姻状况
 *
 */
@Getter
@AllArgsConstructor
public enum MaritalStatus {
    UNMARRIED(0, "未婚"),
    MARRIED(1, "已婚"),
    DIVORCED_WITHOUT_CHILDREN(2, "离异无孩"),
    DIVORCED_WITH_CHILDREN_LOOK(3, "离异带孩"),
    DIVORCED_WITH_CHILDREN_NOT_LOOK(4, "离异不带孩"),
    BEREAVEMENT(5, "丧偶"),
    ;
    /**
     * 索引
     */
    private final Integer index;
    /**
     * 标签
     */
    private final String label;

}
