package io.github.seenings.file.service.impl;

import cn.hutool.core.io.FileUtil;
import io.github.seenings.common.exception.SeenRuntimeException;
import io.github.seenings.common.util.ResUtils;
import io.github.seenings.file.model.StorageTypeAndPath;
import io.github.seenings.file.service.PhotoResourceService;
import io.github.seenings.photo.constant.MinioConstant;
import io.github.seenings.photo.http.HttpPhotoService;
import io.github.seenings.sys.config.SeenConfig;
import io.github.seenings.sys.model.SeenMinioConfig;
import io.minio.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Set;

import static io.github.seenings.photo.constant.MinioConstant.BUCKET_NAME;

/**
 * 照片资源
 */
@Slf4j
@Service
@AllArgsConstructor
public class PhotoResourceServiceImpl implements PhotoResourceService {
    /**
     * 照片
     */
    private HttpPhotoService httpPhotoService;
    /**
     * 配置
     */
    private SeenConfig seenConfig;

    /**
     * 根据照片ID获取文件流
     *
     * @param photoId 照片ID
     * @return 文件流
     */
    @Override
    public InputStream photoIdToInputStream(Integer photoId) {
        StorageTypeAndPath storageTypeAndPath = httpPhotoService.photoIdToStorageTypeAndPath(Set.of(photoId)).get(photoId);
        switch (storageTypeAndPath.storageType()) {
            case LOCAL -> {
                String path = storageTypeAndPath.path();
                String fullPath = seenConfig.getPathConfig().getPhotoPath() + path;
                log.warn("文件路径：{}。", fullPath);
                File photoFile = new File(fullPath);
                if (photoFile.exists() && photoFile.isFile()) {
                    try {
                        return new FileInputStream(fullPath);
                    } catch (FileNotFoundException e) {
                        log.error("", e);
                        return null;
                    }
                }
                String msg = String.format("文件不存在，文件路径：%s", path);
                throw new SeenRuntimeException(msg);
            }
            case MINIO -> {
                String path = storageTypeAndPath.path();
                SeenMinioConfig seenMinioConfig = seenConfig.getSeenMinioConfig();
                try (MinioClient minioClient = MinioClient.builder().endpoint(seenMinioConfig.getEndpoint())
                        .credentials(seenMinioConfig.getAccessKey(), seenMinioConfig.getSecretKey()).build()) {
                    GetObjectResponse getObjectResponse = minioClient.getObject(GetObjectArgs.builder().bucket(BUCKET_NAME)
                            .object(path)
                            .build());
                    if (getObjectResponse == null) {
                        String msg = String.format("文件不存在，令牌桶：%s，文件路径：%s", BUCKET_NAME, path);
                        throw new SeenRuntimeException(msg);
                    }
                    return getObjectResponse;
                } catch (Exception e) {
                    log.error("", e);
                    String msg = String.format("MINIO连接不上，令牌桶：%s，文件路径：%s", BUCKET_NAME, path);
                    throw new SeenRuntimeException(msg);
                }
            }
            case ALIYUN_OSS -> {
                //TODO
                return null;
            }
            case null, default -> {
                return null;
            }
        }
    }
}
