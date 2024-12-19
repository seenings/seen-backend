package com.songchi.seen.photo.http;

import com.songchi.seen.file.model.StorageTypeAndPath;
import com.songchi.seen.sys.constant.PublicConstant;
import com.songchi.seen.sys.constant.ServiceNameConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.Set;

import static com.songchi.seen.sys.constant.SeenConstant.FEIGN_VERSION;

/**
 * HttpPhotoService
 *
 * @author chixuehui
 * @since 2022-12-31
 */
@FeignClient(
        name = ServiceNameConstant.SERVICE_SEEN_FILE,
        contextId = "HttpPhotoService",
        path = FEIGN_VERSION + "file/photo")
public interface HttpPhotoService {
    /**
     * 根据照片ID获取照片路径
     *
     * @param photoIds 照片ID
     * @return 照片ID对应照片路径
     */
    @PostMapping("photo-id-to-photo-url")
    Map<Integer, String> photoIdToPhotoUrl(@RequestBody Set<Integer> photoIds);

    /**
     * 根据照片ID获取长照片路径（含路径头)
     * @see PublicConstant#PHOTO_VERSION
     * @param photoIds 照片ID
     * @return 照片ID对应照片路径
     */
    @PostMapping("photo-id-to-long-photo-url")
    Map<Integer, String> photoIdToLongPhotoUrl(@RequestBody Set<Integer> photoIds);

    /**
     * 设置路径
     * @param path  路径
     * @param userId    用户
     * @return  路径ID
     */
    @PostMapping("set-path")
    Integer setPath(@RequestParam("path") String path, @RequestParam("userId") Integer userId);

    @PostMapping("photo-id-to-storage-type-and-path")
    Map<Integer, StorageTypeAndPath> photoIdToStorageTypeAndPath(@RequestBody Set<Integer> photoIds);

    @PostMapping("photo-id-to-file-name")
    Map<Integer, String> photoIdToFileName(@RequestBody Set<Integer> photoIds);
}
