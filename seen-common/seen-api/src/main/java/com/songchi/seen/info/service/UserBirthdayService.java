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
    Map<Integer, Integer> userIdToYear(Set<Integer> userIds);

    Map<Integer, Integer> userIdToMonth(Set<Integer> userIds);

    Map<Integer, Integer> userIdToDay(Set<Integer> userIds);

    boolean set(Integer userId, Integer year, Integer month, Integer day);
}
