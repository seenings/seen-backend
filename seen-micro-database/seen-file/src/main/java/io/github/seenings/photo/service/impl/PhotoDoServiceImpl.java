package io.github.seenings.photo.service.impl;

import io.github.seenings.file.enumeration.StorageType;
import io.github.seenings.file.model.StorageTypeAndPath;
import io.github.seenings.file.service.FilePOService;
import io.github.seenings.photo.service.PhotoPOService;
import io.github.seenings.photo.service.PhotoDoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 照片处理
 */
@Service
@AllArgsConstructor
public class PhotoDoServiceImpl implements PhotoDoService {
    /**
     * 文件
     */
    private FilePOService filePOService;

    /**
     * 照片
     */
    private PhotoPOService photoPOService;

    /**
     * 根据照片ID获取照片路径
     *
     * @param photoIds 照片ID
     * @return 照片ID对应照片路径
     */
    @Override
    public Map<Integer, String> photoIdToPhotoUrl(Set<Integer> photoIds) {
        Map<Integer, Integer> photoIdToFileIdMap = photoPOService.idToFileId(photoIds);
        Set<Integer> fileIds = new HashSet<>(photoIdToFileIdMap.values());
        Map<Integer, String> fileIdToPathMap = filePOService.idToPath(fileIds);
        return photoIds.stream().parallel().map(n -> {
            Integer fileId = photoIdToFileIdMap.get(n);
            return Map.entry(n, fileIdToPathMap.get(fileId));
        }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * 根据照片ID获取文件
     *
     * @param photoIds 照片ID
     * @return 照片ID对应文件
     */
    @Override
    public Map<Integer, StorageTypeAndPath> photoIdToStorageTypeAndPath(Set<Integer> photoIds) {
        Map<Integer, Integer> photoIdToFileIdMap = photoPOService.idToFileId(photoIds);
        Set<Integer> fileIds = new HashSet<>(photoIdToFileIdMap.values());
        Map<Integer, String> fileIdToPathMap = filePOService.idToPath(fileIds);
        Map<Integer, StorageType> fileIdToStorageTypeMap =
                filePOService.idToStorageType(fileIds);

        return photoIds.stream().parallel().map(n -> {
            Integer fileId = photoIdToFileIdMap.get(n);
            String path = fileIdToPathMap.get(fileId);
            StorageType storageType = fileIdToStorageTypeMap.get(fileId);
            return Map.entry(n, new StorageTypeAndPath(storageType, path));
        }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * 根据照片ID获取文件名
     *
     * @param photoIds 照片ID
     * @return 照片ID对应文件名
     */
    @Override
    public Map<Integer, String> photoIdToFileName(Set<Integer> photoIds) {
        Map<Integer, Integer> photoIdToFileIdMap = photoPOService.idToFileId(photoIds);
        Set<Integer> fileIds = new HashSet<>(photoIdToFileIdMap.values());
        Map<Integer, String> fileIdToNameMap = filePOService.idToName(fileIds);

        return photoIds.stream().parallel().map(n -> {
            Integer fileId = photoIdToFileIdMap.get(n);
            String name = fileIdToNameMap.get(fileId);
            return Map.entry(n, name);
        }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
