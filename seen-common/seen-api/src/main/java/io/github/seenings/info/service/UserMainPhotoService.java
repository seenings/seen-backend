package io.github.seenings.info.service;

import java.util.Map;
import java.util.Set;

/**
 * UserMainPhotoService
 *
 * @author chixuehui
 * @since 2022-10-16
 */
public interface UserMainPhotoService {
    Map<Long, Integer> userIdPhotoId(Set<Long> userIds);

    boolean set(Long userId, Integer photoId);
}
