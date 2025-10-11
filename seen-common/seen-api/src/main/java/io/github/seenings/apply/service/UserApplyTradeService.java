package io.github.seenings.apply.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * UserApplyTradeService
 *
 * @author chixuehui
 * @since 2023-03-05
 */
public interface UserApplyTradeService {
    Map<Integer, List<Long>> applyIdToTradeId(Set<Integer> applyIds);

    Map<Long, Integer> tradeIdToApplyId(Set<Long> tradeIds);

    List<Integer> set(Integer applyId, List<Long> tradeIds);
}
