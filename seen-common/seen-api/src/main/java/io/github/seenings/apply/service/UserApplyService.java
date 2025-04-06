package io.github.seenings.apply.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * UserApplyPOService
 *
 * @author chixuehui
 * @since 2023-01-01
 */
public interface UserApplyService {
    List<Integer> appliedUserIdToApplyIdByPage(Long appliedUserId, int current, int size);

    List<Integer> applyUserIdToApplyIdByPage(Long applyUserId, int current, int size);

    Map<Long, Integer> appliedUserIdToId(Set<Long> appliedUserId, Long userId);

    Map<Integer, Integer> idToTextId(Set<Integer> ids);

    Map<Integer, Long> idToApplyUserId(Set<Integer> ids);

    Map<Integer, Long> idToAppliedUserId(Set<Integer> ids);

    Map<Integer, LocalDateTime> idToCreateTime(Set<Integer> ids);

    Map<Integer, LocalDateTime> idToApplyTime(Set<Integer> ids);

    Integer set(Long userId, Integer textId, Long appliedUserId);
}
