package com.songchi.seen.file.enumeration;

import com.songchi.seen.sys.core.IndexAndLabelEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 存储类型
 */
@Getter
@AllArgsConstructor
public enum StorageType implements IndexAndLabelEnum {

    LOCAL(1, "本地存储"), MINIO(2, "minio存储"), ALIYUN_OSS(3, "阿里云对象存储");
    /**
     * 索引
     */
    private final int index;
    /**
     * 标签
     */
    private final String label;

}
