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
    Map<Integer, Boolean> focusedUserIdToTrue(Set<Integer> focusedUserIds, Integer focusUserId);

    boolean set(Integer focusedUserId, Integer focusUserId);
}
