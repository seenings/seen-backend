package com.songchi.seen.info.service;

import java.util.Map;
import java.util.Set;

/**
 * UserMainPhotoService
 *
 * @author chixuehui
 * @since 2022-10-16
 */
public interface UserMainPhotoService {
    Map<Integer, Integer> userIdPhotoId(Set<Integer> userIds);

    boolean set(Integer userId, Integer photoId);
}
