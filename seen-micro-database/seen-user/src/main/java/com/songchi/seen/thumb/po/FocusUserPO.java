package com.songchi.seen.thumb.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 *
 *
 */
@TableName(value = "focus_user")
@Data
@Accessors(chain = true)
public class FocusUserPO {
    /**
     * 自增ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 被关注者
     */
    private Long focusedUserId;

    /**
     * 关注者
     */
    private Long focusUserId;

    /**
     * 是否删除0未删除
     */
    private Integer deleted;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
