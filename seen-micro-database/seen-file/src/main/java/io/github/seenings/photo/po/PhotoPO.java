package io.github.seenings.photo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 照片
 */
@Data
@TableName("photo")
public class PhotoPO {

    /**
     * 照片ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 文件ID
     */
    private Integer fileId;


}
