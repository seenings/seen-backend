package com.songchi.seen.zone.service;

import java.util.List;

/**
 * ResultRecommendService
 *
 * @author chixuehui
 * @since 2022-10-23
 */
public interface ResultRecommendService {
    List<Integer> userIdToRecommendUserId(Integer userId, String date);

    List<Integer> userIdToRecommendUserId(Integer userId);
}
