package io.github.seenings.photo.http;

import io.github.seenings.file.model.StorageTypeAndPath;
import io.github.seenings.sys.constant.PublicConstant;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.Map;
import java.util.Set;

import static io.github.seenings.sys.constant.SeenConstant.FEIGN_VERSION;

/**
 * HttpPhotoService
 *
 * @author chixuehui
 * @since 2022-12-31
 */
@HttpExchange(
        value = FEIGN_VERSION + "file/photo")
public interface HttpPhotoService {
    /**
     * 根据照片ID获取照片路径
     *
     * @param photoIds 照片ID
     * @return 照片ID对应照片路径
     */
    @PostExchange("photo-id-to-photo-url")
    Map<Integer, String> photoIdToPhotoUrl(@RequestBody Set<Integer> photoIds);

    /**
     * 根据照片ID获取长照片路径（含路径头)
     * @see PublicConstant#PHOTO_VERSION
     * @param photoIds 照片ID
     * @return 照片ID对应照片路径
     */
    @PostExchange("photo-id-to-long-photo-url")
    Map<Integer, String> photoIdToLongPhotoUrl(@RequestBody Set<Integer> photoIds);

    /**
     * 设置路径
     * @param path  路径
     * @param userId    用户
     * @return  路径ID
     */
    @PostExchange("set-path")
    Integer setPath(@RequestParam("path") String path, @RequestParam("userId") Long userId);

    @PostExchange("set-path-minio")
    Integer setPathByMinio(@RequestParam("path") String path, @RequestParam("userId") Long userId);

    @PostExchange("photo-id-to-storage-type-and-path")
    Map<Integer, StorageTypeAndPath> photoIdToStorageTypeAndPath(@RequestBody Set<Integer> photoIds);

    @PostExchange("photo-id-to-file-name")
    Map<Integer, String> photoIdToFileName(@RequestBody Set<Integer> photoIds);
}
