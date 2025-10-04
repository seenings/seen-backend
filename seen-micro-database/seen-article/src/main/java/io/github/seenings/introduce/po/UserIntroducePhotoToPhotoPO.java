package io.github.seenings.introduce.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 介绍照片ID关系
 */
@Data
@Accessors(chain = true)
@TableName(value = "user_introduce_photo_to_photo")
public class UserIntroducePhotoToPhotoPO {
    private Integer userIntroducePhotoId;
    /**
     * 照片ID
     */
    private Integer photoId;
}
