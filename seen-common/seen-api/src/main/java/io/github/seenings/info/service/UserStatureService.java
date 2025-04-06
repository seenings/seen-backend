package io.github.seenings.info.service;

import java.util.Map;
import java.util.Set;

/**
 * UserStatureService
 *
 * @author chixuehui
 * @since 2022-10-16
 */
public interface UserStatureService {

    Map<Long, Integer> userIdToStatureCm(Set<Long> userIds);

    boolean set(Long userId, Integer statureCm);
}
