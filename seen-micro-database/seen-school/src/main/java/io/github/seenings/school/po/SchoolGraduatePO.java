package io.github.seenings.school.po;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 上学状态
 * @author chixuehui
 * @since 2022-10-07
 */
@TableName(value = "school_graduate")
@Data
@Accessors(chain = true)
public class SchoolGraduatePO {
    /**
     * 自增ID
     */
    private Integer id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 是否毕业0（否），1（是）
     */
    private Integer graduated;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
