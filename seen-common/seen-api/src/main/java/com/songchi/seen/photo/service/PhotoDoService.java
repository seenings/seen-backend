package com.songchi.seen.photo.service;

import com.songchi.seen.file.model.StorageTypeAndPath;

import java.util.Map;
import java.util.Set;

/**
 * 照片处理
 */
public interface PhotoDoService {
    Map<Integer, String> photoIdToPhotoUrl(Set<Integer> photoIds);

    Map<Integer, StorageTypeAndPath> photoIdToStorageTypeAndPath(Set<Integer> photoIds);

    Map<Integer, String> photoIdToFileName(Set<Integer> photoIds);
}
