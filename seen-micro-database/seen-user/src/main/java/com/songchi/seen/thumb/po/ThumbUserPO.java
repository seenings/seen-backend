package com.songchi.seen.thumb.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 关注用户
 * @TableName thumb_user
 */
@TableName(value = "thumb_user")
@Data
@Accessors(chain = true)
public class ThumbUserPO {
    /**
     * 自增ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 被赞者
     */
    private Integer thumbedUserId;

    /**
     * 点赞者
     */
    private Integer thumbUserId;

    /**
     * 是否删除0未删除
     */
    private Integer deleted;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}