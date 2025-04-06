package io.github.seenings.calc.service;

import io.github.seenings.info.enumeration.Sex;

import java.util.List;

/**
 * RecommendService
 *
 * @author chixuehui
 * @since 2023-05-21
 */
public interface RecommendService {
    List<Long> mandatoryRecommendUser(Sex recommendSex, int current, int size);

    List<Long> softRecommendUser(Long userId, List<Long> userIds, Integer currentCityId);

    List<Long> createRecommendUser(Long userId);
}
