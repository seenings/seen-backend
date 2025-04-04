package com.songchi.seen.account.service;

import java.util.Map;
import java.util.Set;

/**
 * CoinAccountUserService
 *
 * @author chixuehui
 * @since 2023-01-01
 */
public interface CoinAccountUserService {
    Map<Long, Set<Long>> userIdToAccountId(Set<Long> userIds);

    boolean set(Long accountId, Long userId);
}
