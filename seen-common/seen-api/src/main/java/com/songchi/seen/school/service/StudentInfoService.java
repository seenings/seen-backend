package com.songchi.seen.school.service;

import java.util.Map;
import java.util.Set;

/**
 * StudentInfoService
 *
 * @author chixuehui
 * @since 2022-12-03
 */
public interface StudentInfoService {
    Map<Long, Integer> userIdToSchoolId(Set<Long> userIds);

    boolean set(Long userId, Integer schoolId);
}
