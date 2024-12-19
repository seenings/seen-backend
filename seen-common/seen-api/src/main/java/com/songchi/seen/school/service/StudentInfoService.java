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
    Map<Integer, Integer> userIdToSchoolId(Set<Integer> userIds);

    boolean set(Integer userId, Integer schoolId);
}
