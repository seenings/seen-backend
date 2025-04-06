package io.github.seenings.photo.api.impl;

import cn.hutool.core.io.FileUtil;
import io.github.seenings.file.enumeration.StorageType;
import io.github.seenings.file.model.StorageTypeAndPath;
import io.github.seenings.file.service.FilePOService;
import io.github.seenings.photo.service.MainPhotoPOService;
import io.github.seenings.photo.http.HttpPhotoService;
import io.github.seenings.photo.service.PhotoDoService;
import io.github.seenings.sys.constant.PublicConstant;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static io.github.seenings.sys.constant.SeenConstant.FEIGN_VERSION;

/**
 * PhotoController
 *
 * @author chixuehui
 * @since 2022-12-31
 */
@RestController
@AllArgsConstructor
@RequestMapping(FEIGN_VERSION + "file/photo")
public class PhotoHttpImpl implements HttpPhotoService {

    /**
     * 文件
     */
    private FilePOService filePOService;
    /**
     * 照片处理
     */
    private PhotoDoService photoDoService;
    /**
     * 照片
     */
    private MainPhotoPOService mainPhotoPOService;

    /**
     * 根据照片ID获取照片路径
     *
     * @param photoIds 照片ID
     * @return 照片ID对应照片路径
     */
    @Override
    @PostMapping("photo-id-to-photo-url")
    public Map<Integer, String> photoIdToPhotoUrl(@RequestBody Set<Integer> photoIds) {
        return photoDoService.photoIdToPhotoUrl(photoIds);
    }

    /**
     * 根据照片ID获取长照片路径（含路径头)
     *
     * @param photoIds 照片ID
     * @return 照片ID对应照片路径
     * @see PublicConstant#PHOTO_VERSION
     */
    @Override
    @PostMapping("photo-id-to-long-photo-url")
    public Map<Integer, String> photoIdToLongPhotoUrl(@RequestBody Set<Integer> photoIds) {
        Map<Integer, String> photoIdToPhotoUrlMap = photoDoService.photoIdToPhotoUrl(photoIds);
        return photoIdToPhotoUrlMap.entrySet().stream().parallel().map(n -> {
            if (n.getValue() == null) {
                return null;
            }
            return Map.entry(n.getKey(), PublicConstant.PHOTO_VERSION + n.getValue());
        }).filter(Objects::nonNull).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * 设置路径
     *
     * @param path   路径
     * @param userId 用户
     * @return 路径ID
     */
    @Override
    @PostMapping("set-path")
    public Integer setPath(@RequestParam("path") String path, @RequestParam("userId") Long userId) {
        //不考虑事务,使用分布式回滚法
        Integer fileId = filePOService.set(StorageType.LOCAL, path, FileUtil.getName(path));
        return mainPhotoPOService.set(fileId);
    }

    /**
     * 根据照片ID获取文件
     *
     * @param photoIds 照片ID
     * @return 照片ID对应文件
     */
    @PostMapping("photo-id-to-storage-type-and-path")
    @Override
    public Map<Integer, StorageTypeAndPath> photoIdToStorageTypeAndPath(@RequestBody Set<Integer> photoIds) {
        return photoDoService.photoIdToStorageTypeAndPath(photoIds);
    }

    /**
     * 根据照片ID获取文件名
     *
     * @param photoIds 照片ID
     * @return 照片ID对应文件名
     */
    @PostMapping("photo-id-to-file-name")
    @Override
    public Map<Integer, String> photoIdToFileName(@RequestBody Set<Integer> photoIds) {

        return photoDoService.photoIdToFileName(photoIds);
    }
}
