package com.songchi.seen.introduce.service;

import java.util.Map;
import java.util.Set;

/**
 * UserIntroducePhotoToPhotoPOService
 *
 * @author chixuehui
 * @since 2024-01-07
 */
public interface UserIntroducePhotoToPhotoPOService {
    /**
     * 插入
     *
     * @param userIntroducePhotoId 介绍照片信息ID
     * @param photoId              照片ID
     * @return 是否成功
     */
    boolean insert(Integer userIntroducePhotoId, Integer photoId);

    /**
     * 根据照片介绍ID获取照片ID
     * @param userIntroducePhotoIds 照片介绍ID
     * @return  照片介绍ID对应照片ID
     */
    Map<Integer, Integer> userIntroducePhotoIdToPhotoId(Set<Integer> userIntroducePhotoIds);
}
