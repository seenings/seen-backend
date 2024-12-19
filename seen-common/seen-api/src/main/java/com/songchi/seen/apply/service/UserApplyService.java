package com.songchi.seen.apply.service;

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
    List<Integer> appliedUserIdToApplyIdByPage(Integer appliedUserId, int current, int size);

    List<Integer> applyUserIdToApplyIdByPage(Integer applyUserId, int current, int size);

    Map<Integer, Integer> appliedUserIdToId(Set<Integer> appliedUserId, Integer userId);

    Map<Integer, Integer> idToTextId(Set<Integer> ids);

    Map<Integer, Integer> idToApplyUserId(Set<Integer> ids);

    Map<Integer, Integer> idToAppliedUserId(Set<Integer> ids);

    Map<Integer, LocalDateTime> idToCreateTime(Set<Integer> ids);

    Map<Integer, LocalDateTime> idToApplyTime(Set<Integer> ids);

    Integer set(Integer userId, Integer textId, Integer appliedUserId);
}
