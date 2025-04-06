package io.github.seenings.apply.service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

/**
 * UserApplyRefuseService
 *
 * @author chixuehui
 * @since 2023-03-05
 */
public interface UserApplyRefuseService {
    Map<Integer, LocalDateTime> applyIdToRefuseTime(Set<Integer> applyIds);

    Integer add(Integer applyId, Integer textId);
}
