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
    Map<Integer, Set<Integer>> userIdToAccountId(Set<Integer> userIds);

    boolean set(Integer accountId, Integer userId);
}
