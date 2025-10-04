package io.github.seenings.school.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 学生信息表
 */
@TableName(value = "student_info")
@Data
@Accessors(chain = true)
public class StudentInfoPO {
    /**
     * 自增ID
     */
    private Integer id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 学校id
     */
    private Integer schoolId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     *
     */
    private LocalDateTime updateTime;

    /**
     *
     */
    private Long updateUser;
}
