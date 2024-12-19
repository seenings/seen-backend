package com.songchi.seen.apply.po;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 用户申请查看过
 * 
 * @TableName user_apply_look
 */
@TableName(value = "user_apply_look")
@Data
@Accessors(chain = true)
public class UserApplyLookPO {
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
     * 查看时间
     */
    private LocalDateTime lookTime;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}