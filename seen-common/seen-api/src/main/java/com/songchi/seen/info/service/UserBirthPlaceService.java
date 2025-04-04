package com.songchi.seen.info.service;

import java.util.Map;
import java.util.Set;

/**
 * UserBirthPlaceService
 *
 * @author chixuehui
 * @since 2022-10-16
 */
public interface UserBirthPlaceService {
    Map<Long, Integer> userIdToCityId(Set<Long> userIds);

    Map<Long, Integer> userIdToProvinceId(Set<Long> userIds);

    boolean set(Long userId, Integer provinceId, Integer cityId);
}
