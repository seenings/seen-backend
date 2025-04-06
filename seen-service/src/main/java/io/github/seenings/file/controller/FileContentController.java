package io.github.seenings.file.controller;

import java.io.*;
import java.time.Duration;
import java.util.Map;
import java.util.Set;

import io.github.seenings.file.service.PhotoResourceService;
import org.springframework.core.io.AbstractResource;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.github.seenings.common.model.R;
import io.github.seenings.common.util.ResUtils;
import io.github.seenings.file.model.FileContent;
import io.github.seenings.file.service.FileContentPoService;
import io.github.seenings.file.service.ImageCompressService;
import io.github.seenings.photo.http.HttpPhotoService;
import io.github.seenings.sys.constant.PublicConstant;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 文件内容
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(PublicConstant.REST + "file/file-content")
public class FileContentController {
    /**
     * 照片
     */
    private HttpPhotoService httpPhotoService;
    /**
     * 压缩
     */
    private ImageCompressService imageCompressService;

    private FileContentPoService fileContentPoService;

    /**
     * 照片资源
     */
    private PhotoResourceService photoResourceService;

    /**
     * 根据照片ID获取资源,缓存
     *
     * @param photoId 照片ID
     * @return 资源
     */
    @GetMapping("photo-id-to-resource-by-cache/{photoId}")
    public ResponseEntity<InputStreamResource> photoIdToResourceByCache(@PathVariable Integer photoId) {

        InputStream inputStream = photoResourceService.photoIdToInputStream(photoId);
        if (inputStream == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().cacheControl(CacheControl.maxAge(Duration.ofDays(30L))).body(new InputStreamResource(inputStream));
    }


    @PostMapping("file-id-to-file-content")
    public R<Map<Integer, FileContent>> fileIdToFileContent(@RequestBody Set<Integer> fileIds) {
        Map<Integer, FileContent> fileIdToFileContentMap = fileContentPoService.fileIdToFileContent(fileIds);
        return ResUtils.ok(fileIdToFileContentMap);
    }

    /**
     * 根据照片ID获取文件流
     *
     * @param photoId 照片ID
     * @return 文件流
     */
    @GetMapping("photo-id-to-resources")
    public ResponseEntity<InputStreamResource> photoIdToResources(@RequestParam Integer photoId) {
        InputStream inputStream = photoResourceService.photoIdToInputStream(photoId);
        if (inputStream == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().cacheControl(CacheControl.maxAge(Duration.ofDays(30L))).body(new InputStreamResource(inputStream));
    }

    /**
     * 根据照片ID获取文件流
     *
     * @param photoId 照片ID
     * @return 文件流
     */
    @GetMapping("photo-id-to-resources-by-compress")
    public ResponseEntity<AbstractResource> photoIdToResourcesByCompress(@RequestParam Integer photoId) {
        String photoPath = httpPhotoService.photoIdToPhotoUrl(Set.of(photoId)).get(photoId);
        InputStream inputStream = photoResourceService.photoIdToInputStream(photoId);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            boolean compress = imageCompressService.compress(inputStream, byteArrayOutputStream);
            if (!compress) {
                log.warn("图片压缩失败,文件路径:{}", photoPath);
                return ResponseEntity.noContent().build();
            }
        } catch (IOException e) {
            log.error("", e);
            return ResponseEntity.noContent().build();
        }
        ByteArrayResource byteArrayResource = new ByteArrayResource(byteArrayOutputStream.toByteArray());
        return ResponseEntity.ok().cacheControl(CacheControl.maxAge(Duration.ofDays(1L))).body(byteArrayResource);
    }
}
