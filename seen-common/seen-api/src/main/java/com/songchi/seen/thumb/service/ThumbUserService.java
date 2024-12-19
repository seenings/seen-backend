package com.songchi.seen.thumb.service;

import java.util.Map;
import java.util.Set;

/**
 * ThumbUserService
 *
 * @author chixuehui
 * @since 2023-01-23
 */
public interface ThumbUserService {
    Map<Integer, Boolean> thumbedUserIdToTrue(Set<Integer> thumbedUserId, Integer thumbUserId);

    boolean set(Integer thumbedUserId, Integer thumbUserId);
}
