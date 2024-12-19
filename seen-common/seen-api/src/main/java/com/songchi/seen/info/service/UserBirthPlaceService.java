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
    Map<Integer, Integer> userIdToCityId(Set<Integer> userIds);

    Map<Integer, Integer> userIdToProvinceId(Set<Integer> userIds);

    boolean set(Integer userId, Integer provinceId, Integer cityId);
}
