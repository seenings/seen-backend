package io.github.seenings.thumb.service;

import java.util.Map;
import java.util.Set;

/**
 * ThumbUserService
 *
 * @author chixuehui
 * @since 2023-01-23
 */
public interface ThumbUserService {
    Map<Long, Boolean> thumbedUserIdToTrue(Set<Long> thumbedUserId, Long thumbUserId);

    boolean set(Long thumbedUserId, Long thumbUserId);
}
