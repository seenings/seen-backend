package io.github.seenings.info.service;

import java.util.Map;
import java.util.Set;

/**
 * UserWorkPositionService
 *
 * @author chixuehui
 * @since 2022-10-16
 */
public interface UserWorkPositionService {
    Map<Long, Integer> userIdToPosition(Set<Long> userIds);

    boolean set(Long userId, Integer position);
}
