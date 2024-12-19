package com.songchi.seen.photo.service;

import java.util.Map;
import java.util.Set;

/**
 * 照片
 */
public interface MainPhotoPOService {

    /**
     * 根据照片ID获取文件ID
     *
     * @param ids 照片ID
     * @return 照片ID对应文件ID
     */
    Map<Integer, Integer> idToFileId(Set<Integer> ids);


    /**
     * 设置照片
     *
     * @param fileId 文件ID
     * @return 照片ID
     */
    Integer set(Integer fileId);
}
