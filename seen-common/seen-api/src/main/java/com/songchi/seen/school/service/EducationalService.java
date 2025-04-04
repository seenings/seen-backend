package com.songchi.seen.school.service;

import com.songchi.seen.school.enumeration.Education;

import java.util.Map;
import java.util.Set;

/**
 * EducationalService
 *
 * @author chixuehui
 * @since 2022-10-06
 */
public interface EducationalService {

    Map<Long,Integer> userIdToEducational( Set<Long> userIds);

    boolean set(Long userId, Education education);
}
