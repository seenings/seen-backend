package com.songchi.seen.recommend.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * MiddleUserRecommendServices
 *
 * @author chixuehui
 * @since 2022-10-23
 */
public interface MiddleUserRecommendService {
    Set<Integer> haveUserId(Integer userId, Set<Integer> recommendUserIds);

    Map<Integer, List<Integer>> userIdToRecommendUserId(Set<Integer> userIds, String date);

    int set(Integer userId, String date, List<Integer> recommendUserIds);
}
