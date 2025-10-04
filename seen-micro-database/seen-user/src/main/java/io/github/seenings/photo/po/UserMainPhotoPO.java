package io.github.seenings.photo.po;

import java.time.LocalDateTime;

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
    private Integer id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户照片ID
     */
    private Integer photoId;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
