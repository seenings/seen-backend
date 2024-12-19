package com.songchi.seen.apply.service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

/**
 * UserApplyLookService
 *
 * @author chixuehui
 * @since 2023-01-14
 */
public interface UserApplyLookService {
    Map<Integer, LocalDateTime> applyIdToLookTime(Set<Integer> applyIds);

    Integer set(Integer applyId, LocalDateTime lookTime);
}
