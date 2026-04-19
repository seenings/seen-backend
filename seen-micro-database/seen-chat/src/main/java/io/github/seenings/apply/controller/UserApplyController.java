package io.github.seenings.apply.controller;

import io.github.seenings.apply.http.HttpUserApplyService;
import io.github.seenings.apply.service.UserApplyLookService;
import io.github.seenings.apply.service.UserApplyService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static io.github.seenings.sys.constant.SeenConstant.FEIGN_VERSION;

/**
 * UserApplyController
 *
 * @author chixuehui
 * @since 2023-01-01
 */
@RestController
public class UserApplyController implements HttpUserApplyService {

    @Resource
    private UserApplyService userApplyService;

    @Override
    public Integer set(Long userId, Integer textId, Long appliedUserId) {

        return userApplyService.set(userId, textId, appliedUserId);
    }

    @Override
    public Map<Long, Integer> appliedUserIdToApplyId(Set<Long> appliedUserIds, Long userId) {
        return userApplyService.appliedUserIdToId(appliedUserIds, userId);
    }

    @Override
    public Map<Integer, Integer> applyIdToTextId(Set<Integer> applyIds) {
        return userApplyService.idToTextId(applyIds);
    }

    @Override
    public Map<Integer, Long> applyIdToApplyUserId(Set<Integer> applyIds) {
        return userApplyService.idToApplyUserId(applyIds);
    }

    @Override
    public Map<Integer, Long> applyIdToAppliedUserId(Set<Integer> applyIds) {
        return userApplyService.idToAppliedUserId(applyIds);
    }

    @Override
    public Map<Integer, LocalDateTime> applyIdToCreateTime(Set<Integer> applyIds) {
        return userApplyService.idToCreateTime(applyIds);
    }

    @Override
    public Map<Integer, LocalDateTime> applyIdToApplyTime(Set<Integer> applyIds) {
        return userApplyService.idToApplyTime(applyIds);
    }


    @Resource
    private UserApplyLookService userApplyLookService;

    @Override
    public Map<Integer, LocalDateTime> applyIdToLookTime(
            Set<Integer> applyIds) {
        return userApplyLookService.applyIdToLookTime(applyIds);
    }

    @Override
    public Map<Integer, LocalDateTime> applyIdToAgreeTime(Set<Integer> applyIds) {
        return userApplyLookService.applyIdToLookTime(applyIds);
    }

    @Override
    public List<Integer> appliedUserIdToApplyIdByPage(Long appliedUserId,

                                                      int current, int size) {
        return userApplyService.appliedUserIdToApplyIdByPage(appliedUserId, current, size);
    }

    @Override
    public List<Integer> applyUserIdToApplyIdByPage(Long applyUserId,

                                                    int current, int size) {
        return userApplyService.applyUserIdToApplyIdByPage(applyUserId, current, size);
    }
}
