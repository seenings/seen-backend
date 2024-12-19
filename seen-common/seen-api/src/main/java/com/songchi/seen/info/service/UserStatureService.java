package com.songchi.seen.info.service;

import java.util.Map;
import java.util.Set;

/**
 * UserStatureService
 *
 * @author chixuehui
 * @since 2022-10-16
 */
public interface UserStatureService {

    Map<Integer, Integer> userIdToStatureCm(Set<Integer> userIds);

    boolean set(Integer userId, Integer statureCm);
}
