package com.songchi.seen.file.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 文件内容
 *
 * @author chixuehui
 * @since 2022-05-03
 */
@Data
@Accessors(chain = true)
public class FileContent {
    /**
     * 自增ID
     */
    private Integer id;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 文件内容
     */
    private byte[] fileContent;
}
