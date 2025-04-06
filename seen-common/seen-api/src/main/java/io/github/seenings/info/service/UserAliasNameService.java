package io.github.seenings.info.service;

import java.util.Map;
import java.util.Set;

/**
 * UserAliasNameService
 *
 * @author chixuehui
 * @since 2022-10-16
 */
public interface UserAliasNameService {
    Map<Long, String> userIdToAliasName(Set<Long> userIds);

    boolean set(Long userId, String aliasName);
}
