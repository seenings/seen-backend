package io.github.seenings.chat.service.impl;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import io.github.seenings.apply.http.HttpUserApplyAgreeService;
import io.github.seenings.apply.http.HttpUserApplyLookService;
import io.github.seenings.apply.http.HttpUserApplyRefuseService;
import io.github.seenings.apply.http.HttpUserApplyService;
import io.github.seenings.chat.enumeration.ApplyStatus;
import io.github.seenings.chat.service.ApplyService;
import io.github.seenings.chat.utils.ChatApplyUtils;

import cn.hutool.core.lang.Pair;
import jakarta.annotation.Resource;

/**
 * ApplyServiceImpl
 *
 * @author chixuehui
 * @since 2023-03-05
 */
@Service
public class ApplyServiceImpl implements ApplyService {

    @Resource
    private HttpUserApplyService httpUserApplyService;

    @Resource
    private HttpUserApplyAgreeService httpUserApplyAgreeService;

    @Resource
    private HttpUserApplyLookService httpUserApplyLookService;
    @Resource
    private HttpUserApplyRefuseService httpUserApplyRefuseService;

    @Override
    public Map<Integer, ApplyStatus> applyIdToApplyStatus(Set<Integer> applyIds) {
        // 1.同意
        Map<Integer, LocalDateTime> applyIdToAgreeTimeMap = httpUserApplyAgreeService.applyIdToAgreeTime(applyIds);
        // 2.拒绝
        Map<Integer, LocalDateTime> applyIdToRefuseTimeMap = httpUserApplyRefuseService.applyIdToRefuseTime(applyIds);
        // 3.过期
        Map<Integer, LocalDateTime> applyIdToApplyTimeMap = httpUserApplyService.applyIdToApplyTime(applyIds);
        // 4.查看
        Map<Integer, LocalDateTime> applyIdToLookTimeMap = httpUserApplyLookService.applyIdToLookTime(applyIds);
        // 5.无
        LocalDateTime now = LocalDateTime.now();

        return applyIds
                .stream().parallel()
                .map(applyId -> {
                    LocalDateTime agreeTime = applyIdToAgreeTimeMap.get(applyId);
                    LocalDateTime refuseTime = applyIdToRefuseTimeMap.get(applyId);
                    LocalDateTime applyTime = applyIdToApplyTimeMap.get(applyId);
                    LocalDateTime lookTime = applyIdToLookTimeMap.get(applyId);
                    ApplyStatus applyStatus = ChatApplyUtils.toApplyStatus(agreeTime, refuseTime, applyTime, lookTime,
                            now);
                    return Pair.of(applyId, applyStatus);
                }).collect(Collectors.toMap(Pair::getKey, Pair::getValue));
    }
}
