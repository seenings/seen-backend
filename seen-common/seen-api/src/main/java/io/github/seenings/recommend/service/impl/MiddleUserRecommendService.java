package io.github.seenings.recommend.service.impl;

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
    Set<Long> haveUserId(Long userId, Set<Long> recommendUserIds);

    Map<Long, List<Long>> userIdToRecommendUserId(Set<Long> userIds, String date);

    int set(Long userId, String date, List<Long> recommendUserIds);
}
