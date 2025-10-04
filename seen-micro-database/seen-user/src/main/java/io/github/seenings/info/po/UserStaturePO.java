package io.github.seenings.info.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 用户身高
 */
@TableName(value = "user_stature")
@Data
@Accessors(chain = true)
public class UserStaturePO implements Serializable {
    /**
     * 自增ID
     */
    private Integer id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 身高（厘米）
     */
    private Integer statureCm;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
