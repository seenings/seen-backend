package com.songchi.seen.info.service;

import java.util.Map;
import java.util.Set;

/**
 * UserWorkPositionService
 *
 * @author chixuehui
 * @since 2022-10-16
 */
public interface UserWorkPositionService {
    Map<Integer, Integer> userIdToPosition(Set<Integer> userIds);

    boolean set(Integer userId, Integer position);
}
