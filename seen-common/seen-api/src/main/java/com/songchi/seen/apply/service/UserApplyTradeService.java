package com.songchi.seen.apply.service;

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
    Map<Integer, List<Integer>> applyIdToTradeId(Set<Integer> applyIds);

    Map<Integer, Integer> tradeIdToApplyId(Set<Integer> tradeIds);

    List<Integer> set(Integer applyId, List<Integer> tradeIds);
}
