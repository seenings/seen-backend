package com.songchi.seen.info.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * UserCurrentResidenceService
 *
 * @author chixuehui
 * @since 2022-10-16
 */
public interface UserCurrentResidenceService {
    List<Integer> currentResidenceCityToUserId(Integer cityId, int current, int size);

    Map<Integer, Integer> userIdToCityId(Set<Integer> userIds);

    Map<Integer, Integer> userIdToProvinceId(Set<Integer> userIds);

    boolean set(Integer userId, Integer provinceId, Integer cityId);
}
