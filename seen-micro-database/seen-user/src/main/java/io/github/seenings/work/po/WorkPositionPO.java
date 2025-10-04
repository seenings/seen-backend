package io.github.seenings.work.po;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 工作职位
 */
@TableName(value = "work_position")
@Data
@Accessors(chain = true)
public class WorkPositionPO implements Serializable {
    /**
     * 自增ID
     */
    private Integer id;

    /**
     * 职位名
     */
    private String positionName;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
