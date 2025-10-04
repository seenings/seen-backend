package io.github.seenings.apply.po;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 用户申请
 *
 */
@TableName(value = "user_apply")
@Data
@Accessors(chain = true)
public class UserApplyPO {
    /**
     * 自增ID，申请单ID
     */
    private Integer id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 申请消息，对应text表
     */
    private Integer textId;

    /**
     * 被申请方用户ID
     */
    private Long appliedUserId;

    /**
     * 申请时间
     */
    private LocalDateTime applyTime;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
