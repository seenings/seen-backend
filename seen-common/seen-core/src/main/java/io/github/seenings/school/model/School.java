package io.github.seenings.school.model;

import lombok.Data;

@Data
public class School {
    /** 序号 */
    private Integer serialNo;
    /** 学校名称 */
    private String schoolName;
    /** 学校标识码（12位，用String防止精度丢失） */
    private String schoolCode;
    /** 主管部门 */
    private String authority;
    /** 所在地 */
    private String location;
    /** 办学层次 */
    private String educationLevel;
    /** 备注 */
    private String remark;
}
