package com.songchi.seen.apply.service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

/**
 * UserApplyAgreeService
 *
 * @author chixuehui
 * @since 2023-01-14
 */
public interface UserApplyAgreeService {
    Map<Integer, LocalDateTime> applyIdToAgreeTime
            (Set<Integer> applyIds);

    Integer set(Integer applyId, LocalDateTime agreeTime);
}
