package com.songchi.seen.file.model;

import com.songchi.seen.file.enumeration.StorageType;

/**
 * 文件
 * @param storageType   存储类型
 * @param path 路径
 */
public record StorageTypeAndPath(StorageType storageType, String path) {
}
