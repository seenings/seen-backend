package io.github.seenings.apply.po;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 用户申请拒绝
 */
@TableName(value = "user_apply_refuse")
@Data
@Accessors(chain = true)
public class UserApplyRefusePO {
    /**
     * 自增ID
     */
    private Integer id;

    /**
     * 申请单ID
     */
    private Integer applyId;

    /**
     * 拒绝消息，对应text表
     */
    private Integer textId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}
