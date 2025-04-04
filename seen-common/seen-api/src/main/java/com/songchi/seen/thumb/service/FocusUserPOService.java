package com.songchi.seen.thumb.service;

import java.util.Map;
import java.util.Set;

/**
 * FocusUserPOService
 *
 * @author chixuehui
 * @since 2023-01-23
 */
public interface FocusUserPOService {
    Map<Long, Boolean> focusedUserIdToTrue(Set<Long> focusedUserIds, Long focusUserId);

    boolean set(Long focusedUserId, Long focusUserId);
}
