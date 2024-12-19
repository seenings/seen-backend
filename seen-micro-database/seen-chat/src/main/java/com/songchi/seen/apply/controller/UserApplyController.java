package com.songchi.seen.apply.controller;

import com.songchi.seen.apply.http.HttpUserApplyService;
import com.songchi.seen.apply.service.UserApplyLookService;
import com.songchi.seen.apply.service.UserApplyService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.songchi.seen.sys.constant.SeenConstant.FEIGN_VERSION;

/**
 * UserApplyController
 *
 * @author chixuehui
 * @since 2023-01-01
 */
@RestController
@RequestMapping(FEIGN_VERSION + "chat/apply")
public class UserApplyController implements HttpUserApplyService {

    @Resource
    private UserApplyService userApplyService;

    @Override
    @PostMapping("set")
    public Integer set(
            @RequestParam("userId") Integer userId,
            @RequestParam("textId") Integer textId,
            @RequestParam("appliedUserId") Integer appliedUserId) {

        return userApplyService.set(userId, textId, appliedUserId);
    }

    @Override
    @PostMapping("applied-user-id-to-apply-id")
    public Map<Integer, Integer> appliedUserIdToApplyId(
            @RequestBody Set<Integer> appliedUserIds, @RequestParam("userId") Integer userId) {
        return userApplyService.appliedUserIdToId(appliedUserIds, userId);
    }

    @Override
    @PostMapping("apply-id-to-text-id")
    public Map<Integer, Integer> applyIdToTextId(@RequestBody Set<Integer> applyIds) {
        return userApplyService.idToTextId(applyIds);
    }

    @Override
    @PostMapping("apply-id-to-apply-user-id")
    public Map<Integer, Integer> applyIdToApplyUserId(@RequestBody Set<Integer> applyIds) {
        return userApplyService.idToApplyUserId(applyIds);
    }
    @Override
    @PostMapping("apply-id-to-applied-user-id")
    public Map<Integer, Integer> applyIdToAppliedUserId(@RequestBody Set<Integer> applyIds) {
        return userApplyService.idToAppliedUserId(applyIds);
    }

    @Override
    @PostMapping("apply-id-to-create-time")
    public Map<Integer, LocalDateTime> applyIdToCreateTime(@RequestBody Set<Integer> applyIds) {
        return userApplyService.idToCreateTime(applyIds);
    }

    @Override
    @PostMapping("apply-id-to-apply-time")
    public Map<Integer, LocalDateTime> applyIdToApplyTime(@RequestBody Set<Integer> applyIds) {
        return userApplyService.idToApplyTime(applyIds);
    }


    @Resource
    private UserApplyLookService userApplyLookService;

    @Override
    @PostMapping("apply-id-to-look-time")
    public Map<Integer, LocalDateTime> applyIdToLookTime(
            @RequestBody Set<Integer> applyIds) {
        return userApplyLookService.applyIdToLookTime(applyIds);
    }

    @Override
    @PostMapping("apply-id-to-agree-time")
    public Map<Integer, LocalDateTime> applyIdToAgreeTime(
            @RequestBody Set<Integer> applyIds) {
        return userApplyLookService.applyIdToLookTime(applyIds);
    }

    @Override
    @PostMapping("applied-user-id-apply-id-by-page")
    public List<Integer> appliedUserIdToApplyIdByPage(@RequestParam("appliedUserId") Integer appliedUserId,
                                                      @RequestParam("current")
                                                      int current, @RequestParam("size") int size) {
        return userApplyService.appliedUserIdToApplyIdByPage(appliedUserId, current, size);
    }
    @Override
    @PostMapping("apply-user-id-to-apply-id-by-page")
    public List<Integer> applyUserIdToApplyIdByPage(@RequestParam("applyUserId") Integer applyUserId,
                                                    @RequestParam("current")
                                                    int current, @RequestParam("size") int size) {
        return userApplyService.applyUserIdToApplyIdByPage(applyUserId, current, size);
    }
}
