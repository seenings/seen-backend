package com.songchi.seen.introduce.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * UserIntroducePO
 *
 * @author chixuehui
 * @since 2022-11-27
 */
@Data
@Accessors(chain = true)
@TableName("user_introduce")
public class UserIntroducePO {
    /**
     * 自增ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 介绍类型
     */
    private Integer introduceType;
    /**
     * 文本ID
     */
    private Integer textId;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
