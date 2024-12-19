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
    Map<Integer, Integer> userIdToGraduated(Set<Integer> userIds);

    boolean set(Integer userId, Integer graduated);
}
