package com.songchi.seen.apply.po;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 用户申请同意
 *
 */
@TableName(value = "user_apply_agree")
@Data
@Accessors(chain = true)
public class UserApplyAgreePO {
    /**
     * 自增ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 申请单ID
     */
    private Integer applyId;

    /**
     * 同意时间
     */
    private LocalDateTime agreeTime;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
