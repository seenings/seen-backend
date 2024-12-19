package com.songchi.seen.calc.service;

import com.songchi.seen.info.enumeration.Sex;

import java.util.List;

/**
 * RecommendService
 *
 * @author chixuehui
 * @since 2023-05-21
 */
public interface RecommendService {
    List<Integer> mandatoryRecommendUser(Sex recommendSex, int current, int size);

    List<Integer> softRecommendUser(Integer userId, List<Integer> userIds, Integer currentCityId);

    List<Integer> createRecommendUser(Integer userId);
}
