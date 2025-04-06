package io.github.seenings.photo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 主要的照片
 */
@Data
@TableName("main_photo")
public class MainPhotoPO {

    /**
     * 照片ID
     */
    private Integer id;
    /**
     * 文件ID
     */
    private Integer fileId;


}
