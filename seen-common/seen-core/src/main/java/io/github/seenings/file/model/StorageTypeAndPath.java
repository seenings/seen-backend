package io.github.seenings.file.model;

import io.github.seenings.file.enumeration.StorageType;

/**
 * 文件
 * @param storageType   存储类型
 * @param path 路径
 */
public record StorageTypeAndPath(StorageType storageType, String path) {
}
