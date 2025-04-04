package com.songchi.seen.info.service;

import java.util.Map;
import java.util.Set;

/**
 * UserAuthService
 *
 * @author chixuehui
 * @since 2022-10-16
 */
public interface UserAuthService {
    Map<Long, Integer> userIdToUserAuth(Set<Long> userIds);

    boolean set(Long userId, Integer authStatus);
}
