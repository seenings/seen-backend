package com.songchi.seen.school.service;

import java.util.Map;
import java.util.Set;

/**
 * SchoolGraduateService
 *
 * @author chixuehui
 * @since 2022-10-07
 */
public interface SchoolGraduateService {
    Map<Long, Integer> userIdToGraduated(Set<Long> userIds);

    boolean set(Long userId, Integer graduated);
}
