package com.songchi.seen.info.service;

import java.util.Map;
import java.util.Set;

/**
 * UserWeightService
 *
 * @author chixuehui
 * @since 2022-10-16
 */
public interface UserWeightService {
    Map<Long, Integer> userIdToWeightKg(Set<Long> userIds);

    boolean set(Long userId, Integer weightKg);
}
