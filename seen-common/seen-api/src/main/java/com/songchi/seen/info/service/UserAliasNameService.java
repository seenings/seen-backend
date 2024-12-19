package com.songchi.seen.info.service;

import java.util.Map;
import java.util.Set;

/**
 * UserAliasNameService
 *
 * @author chixuehui
 * @since 2022-10-16
 */
public interface UserAliasNameService {
    Map<Integer, String> userIdToAliasName(Set<Integer> userIds);

    boolean set(Integer userId, String aliasName);
}
