package io.github.seenings.file.po;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.seenings.file.enumeration.StorageType;
import lombok.Data;

/**
 * 文件
 */
@Data
@TableName("file")
public class FilePO {

    /**
     * 文件ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 文件存储类型
     *
     * @see StorageType
     */
    private Integer storageType;
    /**
     * 路径
     */
    private String path;
    /**
     * 文件名称
     */
    private String name;

}
