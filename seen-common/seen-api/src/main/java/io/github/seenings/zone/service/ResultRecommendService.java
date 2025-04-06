package io.github.seenings.zone.service;

import java.util.List;

/**
 * ResultRecommendService
 *
 * @author chixuehui
 * @since 2022-10-23
 */
public interface ResultRecommendService {
    List<Long> userIdToRecommendUserId(Long userId, String date);

    List<Long> userIdToRecommendUserId(Long userId);
}
