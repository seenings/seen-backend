package com.songchi.seen.photo.po;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 用户头像主照片
 *
 */
@TableName(value = "user_main_photo")
@Data
@Accessors(chain = true)
public class UserMainPhotoPO {
    /**
     * 自增ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 用户照片ID
     */
    private Integer photoId;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
