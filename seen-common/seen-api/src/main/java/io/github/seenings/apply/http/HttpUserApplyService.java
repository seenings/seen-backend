package io.github.seenings.apply.http;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.github.seenings.sys.constant.SeenConstant;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

/**
 * 申请好友
 */
@HttpExchange(SeenConstant.SEEN_SMALL + "chat/apply")
public interface HttpUserApplyService {
    @PostExchange("set")
    Integer set(
            @RequestParam("userId") Long userId,
            @RequestParam("textId") Integer textId,
            @RequestParam("appliedUserId") Long appliedUserId);

    @PostExchange("applied-user-id-to-apply-id")
    Map<Long, Integer> appliedUserIdToApplyId(
            @RequestBody Set<Long> appliedUserIds, @RequestParam("userId") Long userId);

    @PostExchange("apply-id-to-text-id")
    Map<Integer, Integer> applyIdToTextId(@RequestBody Set<Integer> applyIds);

    @PostExchange("apply-id-to-apply-user-id")
    Map<Integer, Long> applyIdToApplyUserId(@RequestBody Set<Integer> applyIds);

    @PostExchange("apply-id-to-applied-user-id")
    Map<Integer, Long> applyIdToAppliedUserId(@RequestBody Set<Integer> applyIds);

    @PostExchange("apply-id-to-create-time")
    Map<Integer, LocalDateTime> applyIdToCreateTime(@RequestBody Set<Integer> applyIds);

    @PostExchange("apply-id-to-apply-time")
    Map<Integer, LocalDateTime> applyIdToApplyTime(@RequestBody Set<Integer> applyIds);

    @PostExchange("apply-id-to-look-time")
    Map<Integer, LocalDateTime> applyIdToLookTime(
            @RequestBody Set<Integer> applyIds);

    @PostExchange("apply-id-to-agree-time")
    Map<Integer, LocalDateTime> applyIdToAgreeTime(
            @RequestBody Set<Integer> applyIds);

    @PostExchange("applied-user-id-apply-id-by-page")
    List<Integer> appliedUserIdToApplyIdByPage(@RequestParam("appliedUserId") Long appliedUserId,
                                               @RequestParam("current")
                                               int current, @RequestParam("size") int size);

    @PostExchange("apply-user-id-to-apply-id-by-page")
    List<Integer> applyUserIdToApplyIdByPage(@RequestParam("applyUserId") Long applyUserId,
                                             @RequestParam("current")
                                             int current, @RequestParam("size") int size);
}
