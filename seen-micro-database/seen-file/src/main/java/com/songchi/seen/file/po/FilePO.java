package com.songchi.seen.file.po;


import com.baomidou.mybatisplus.annotation.TableName;
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
    private Integer id;
    /**
     * 文件存储类型
     *
     * @see com.songchi.seen.file.enumeration.StorageType
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
