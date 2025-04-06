package io.github.seenings.info.model;

import io.github.seenings.school.enumeration.Education;
import io.github.seenings.info.enumeration.Sex;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * BasicInfo
 *
 * @author chixuehui
 * @since 2022-10-06
 */
@Data
@Accessors(chain = true)
public class BasicInfo {
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 性别
     */
    private Sex sex;
    /**
     * 学历
     */
    private Education education;
    /**
     * 出生年份
     */
    private Integer birthYear;
    /**
     * 已毕业
     */
    private Boolean graduated;
}
