package com.songchi.seen.photo.controller;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.List;

import com.songchi.seen.photo.constant.MinioConstant;
import com.songchi.seen.sys.model.SeenMinioConfig;
import io.github.seenings.time.component.NowComponent;
import io.minio.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import com.songchi.seen.common.model.R;
import com.songchi.seen.common.util.ResUtils;
import com.songchi.seen.photo.http.HttpPhotoService;
import com.songchi.seen.sys.config.SeenConfig;
import com.songchi.seen.sys.constant.PublicConstant;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.net.URLDecoder;
import cn.hutool.core.util.RandomUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.songchi.seen.photo.constant.MinioConstant.BUCKET_NAME;

/**
 * <p>
 * 图像 前端控制器
 * </p>
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(PublicConstant.REST + "photo/photo")
public class PhotoController {

    private HttpPhotoService httpPhotoService;

    private SeenConfig seenConfig;
    /**
     * 当前时间组件
     */
    private NowComponent nowComponent;

    @PostMapping("upload")
    public R<Integer> upload(@SessionAttribute(PublicConstant.USER_ID) Integer userId, MultipartFile file) {
        if (file == null || file.isEmpty()) {
            String msg = "文件为空。";
            log.error(msg);
            return ResUtils.error(msg);
        }
        String originalFilename = URLDecoder.decode(file.getOriginalFilename(), StandardCharsets.UTF_8);
        String fileName = nowComponent.nowToSeventeenFormatDate() + "-" + RandomUtil.randomNumbers(4) + "-" + originalFilename;
        String relativePath = nowComponent.nowToBasicIsoDate() + File.separator + fileName;
        String realPath = seenConfig.getPathConfig().getPhotoPath() + relativePath;
        long size = file.getSize();
        try {
            FileUtil.writeFromStream(file.getInputStream(), Paths.get(realPath).toFile());
        } catch (IOException e) {
            log.error("", e);
            return ResUtils.error(e.getMessage());
        }
        Integer pathId = httpPhotoService.setPath(relativePath, userId);

        //TODO
        SeenMinioConfig seenMinioConfig = seenConfig.getSeenMinioConfig();
        try (MinioClient minioClient = MinioClient.builder().endpoint(seenMinioConfig.getEndpoint()).credentials(seenMinioConfig.getAccessKey(), seenMinioConfig.getSecretKey()).build()) {
            // Make 'asiatrip' bucket if not exist.
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(MinioConstant.BUCKET_NAME).build());
            if (!found) {
                log.warn("Bucket '{} 'not exists.", BUCKET_NAME);
                // Make a new bucket called 'asiatrip'.
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(MinioConstant.BUCKET_NAME).build());
            }
            // Upload '/home/user/Photos/asiaphotos.zip' as object name 'asiaphotos-2015.zip' to bucket
            // 'asiatrip'.
            minioClient.putObject(PutObjectArgs.builder().bucket(BUCKET_NAME).stream(FileUtil.getInputStream(realPath), size, -1).object(relativePath).build());
        } catch (Exception e) {
            log.error("", e);
            return ResUtils.error(e.getMessage());
        }

        return ResUtils.ok(pathId);
    }

    @PostMapping("uploads")
    public R<List<Integer>> uploads(@SessionAttribute Integer userId, List<MultipartFile> files) {
        if (files == null || files.isEmpty()) {
            String msg = "文件为空。";
            log.error(msg);
            return ResUtils.error(msg);
        }
        List<Integer> pathIds = CollUtil.newArrayList();
        try {
            for (MultipartFile file : files) {
                String originalFilename = URLDecoder.decode(file.getOriginalFilename(), StandardCharsets.UTF_8);
                String relativePath = nowComponent.nowToBasicIsoDate() + File.separator + nowComponent.nowToSeventeenFormatDate() + "-" + RandomUtil.randomNumbers(4) + "-" + originalFilename;
                FileUtil.writeFromStream(file.getInputStream(), Paths.get(seenConfig.getPathConfig().getPhotoPath(), relativePath).toFile());
                Integer pathId = httpPhotoService.setPath(relativePath, userId);
                pathIds.add(pathId);
            }
        } catch (IOException e) {
            log.error("", e);
            return ResUtils.error(e.getMessage());
        }
        return ResUtils.ok(pathIds);
    }

}
