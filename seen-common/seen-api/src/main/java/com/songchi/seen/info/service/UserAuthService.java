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
    Map<Integer, Integer> userIdToUserAuth(Set<Integer> userIds);

    boolean set(Integer userId, Integer authStatus);
}
