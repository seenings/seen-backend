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

    Map<Integer,Integer> userIdToEducational( Set<Integer> userIds);

    boolean set(Integer userId, Education education);
}
