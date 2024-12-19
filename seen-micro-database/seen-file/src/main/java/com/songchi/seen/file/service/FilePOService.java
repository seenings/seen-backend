package com.songchi.seen.file.service;

import com.songchi.seen.file.enumeration.StorageType;

import java.util.Map;
import java.util.Set;

/**
 * 文件
 */
public interface FilePOService {
    /**
     * 根据文件ID获取存储类型
     *
     * @param ids 文件ID
     * @return 文件ID对应存储类型
     */
    Map<Integer, StorageType> idToStorageType(Set<Integer> ids);

    /**
     * 根据文件ID获取名称
     *
     * @param ids 文件ID
     * @return 文件ID对应名称
     */
    Map<Integer, String> idToName(Set<Integer> ids);

    /**
     * 根据文件ID获取路径
     *
     * @param ids 文件ID
     * @return 文件ID对应路径
     */
    Map<Integer, String> idToPath(Set<Integer> ids);

    /**
     * 设置文件
     *
     * @param storageType 存储类型
     * @param path        路径
     * @param name        名字
     * @return 文件ID
     */
    Integer set(StorageType storageType, String path, String name);
}
