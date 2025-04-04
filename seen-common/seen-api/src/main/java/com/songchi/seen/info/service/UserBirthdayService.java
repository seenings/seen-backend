package com.songchi.seen.info.service;

import java.util.Map;
import java.util.Set;

/**
 * UserBirthdayService
 *
 * @author chixuehui
 * @since 2022-10-07
 */
public interface UserBirthdayService {
    Map<Long, Integer> userIdToYear(Set<Long> userIds);

    Map<Long, Integer> userIdToMonth(Set<Long> userIds);

    Map<Long, Integer> userIdToDay(Set<Long> userIds);

    boolean set(Long userId, Integer year, Integer month, Integer day);
}
