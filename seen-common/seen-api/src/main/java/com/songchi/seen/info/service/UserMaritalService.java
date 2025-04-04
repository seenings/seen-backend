package com.songchi.seen.info.service;

import com.songchi.seen.info.enumeration.MaritalStatus;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * UserMaritalService
 *
 * @author chixuehui
 * @since 2023-02-11
 */
public interface UserMaritalService {
    Map<Long, Integer> userIdToMaritalStatus(Set<Long> userIds);

    List<Long> maritalStatusToUserId(MaritalStatus maritalStatus, int current, int size);

    boolean set(Long userId, MaritalStatus maritalStatus);
}
