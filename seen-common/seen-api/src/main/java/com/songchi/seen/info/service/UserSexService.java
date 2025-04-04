package com.songchi.seen.info.service;

import com.songchi.seen.info.enumeration.Sex;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * UserSexService
 *
 * @author chixuehui
 * @since 2022-10-06
 */
public interface UserSexService {
    Map<Long, Integer> userIdToSex(Set<Long> userIds);

    long sexCount(Sex sex);

    List<Long> sexToUserId(Sex sex, int current, int size);

    boolean set(Long userId, Sex sex);
}
