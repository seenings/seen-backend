package io.github.seenings.apply.http;

import static io.github.seenings.sys.constant.SeenConstant.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import io.github.seenings.sys.constant.ServiceNameConstant;

/**
 * HttpUserApplyService
 *
 * @author chixuehui
 * @since 2023-01-01
 */
@FeignClient(
        name = ServiceNameConstant.SERVICE_SEEN_CHAT,
        path = FEIGN_VERSION + "chat/apply",
        contextId = "HttpUserApplyService")
public interface HttpUserApplyService {
    @PostMapping("set")
    Integer set(
            @RequestParam("userId") Long userId,
            @RequestParam("textId") Integer textId,
            @RequestParam("appliedUserId") Long appliedUserId);

    @PostMapping("applied-user-id-to-apply-id")
    Map<Long, Integer> appliedUserIdToApplyId(
            @RequestBody Set<Long> appliedUserIds, @RequestParam("userId") Long userId);

    @PostMapping("apply-id-to-text-id")
    Map<Integer, Integer> applyIdToTextId(@RequestBody Set<Integer> applyIds);

    @PostMapping("apply-id-to-apply-user-id")
    Map<Integer, Long> applyIdToApplyUserId(@RequestBody Set<Integer> applyIds);

    @PostMapping("apply-id-to-applied-user-id")
    Map<Integer, Long> applyIdToAppliedUserId(@RequestBody Set<Integer> applyIds);

    @PostMapping("apply-id-to-create-time")
    Map<Integer, LocalDateTime> applyIdToCreateTime(@RequestBody Set<Integer> applyIds);

    @PostMapping("apply-id-to-apply-time")
    Map<Integer, LocalDateTime> applyIdToApplyTime(@RequestBody Set<Integer> applyIds);

    @PostMapping("apply-id-to-look-time")
    Map<Integer, LocalDateTime> applyIdToLookTime(
            @RequestBody Set<Integer> applyIds);

    @PostMapping("apply-id-to-agree-time")
    Map<Integer, LocalDateTime> applyIdToAgreeTime(
            @RequestBody Set<Integer> applyIds);

    @PostMapping("applied-user-id-apply-id-by-page")
    List<Integer> appliedUserIdToApplyIdByPage(@RequestParam("appliedUserId") Long appliedUserId,
                                               @RequestParam("current")
                                               int current, @RequestParam("size") int size);

    @PostMapping("apply-user-id-to-apply-id-by-page")
    List<Integer> applyUserIdToApplyIdByPage(@RequestParam("applyUserId") Long applyUserId,
                                             @RequestParam("current")
                                             int current, @RequestParam("size") int size);
}
