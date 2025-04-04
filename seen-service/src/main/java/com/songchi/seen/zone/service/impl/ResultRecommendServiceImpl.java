package com.songchi.seen.zone.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.songchi.seen.calc.http.HttpRecommendService;
import com.songchi.seen.calc.service.AsyncCalcService;
import com.songchi.seen.core.util.CollUtils;
import com.songchi.seen.recommend.http.HttpMiddleUserRecommendService;
import com.songchi.seen.zone.service.ResultRecommendService;
import io.github.seenings.time.component.NowComponent;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * RecommendServiceImpl
 */
@Service
@AllArgsConstructor
public class ResultRecommendServiceImpl implements ResultRecommendService {

    private AsyncCalcService asyncCalcService;

    private HttpMiddleUserRecommendService httpMiddleUserRecommendService;

    private HttpRecommendService httpRecommendService;

    @Override
    public List<Long> userIdToRecommendUserId(Long userId, String date) {
        List<Long> recommendUserIds = httpMiddleUserRecommendService.userIdToRecommendUserId(Collections.singleton(userId), date).get(userId);
        if (CollUtils.isNotEmpty(recommendUserIds)) {
            return recommendUserIds;
        } else {
            // 生成
            recommendUserIds = httpRecommendService.createRecommendUser(userId);
            httpMiddleUserRecommendService.set(userId, date, recommendUserIds);
            return recommendUserIds;
        }
    }

    @Override
    public List<Long> userIdToRecommendUserId(Long userId) {
        // 每天的推荐数据在12点之后推送
        LocalDateTime now = LocalDateTime.now();
        boolean pm = DateUtil.isPM(io.github.seenings.time.util.DateUtil.localDateTimeToDate(now));
        List<Long> recommendUserIds;
        String date = io.github.seenings.time.util.DateUtil.localDateTimeToBasicIsoDate(now);
        if (pm) {
            // 如果是下午
            recommendUserIds = userIdToRecommendUserId(userId, date);
        } else {
            // 如果是上午，返回上一日的推荐数据，并异步生成当日的推荐数据，三分之一的概率去生成当日推荐数据
            String lastDay = io.github.seenings.time.util.DateUtil.basicIsoDateOffsetDays(date, -1);
            recommendUserIds = userIdToRecommendUserId(userId, lastDay);
            // 并异步生成当日的推荐数据，三分之一的概率去生成当日推荐数据，将流量均匀分配
            int randomInt = RandomUtil.randomInt(1, 3);
            if (1 == randomInt) {
                asyncCalcService.userIdToRecommendUserId(userId, date);
            }
        }
        return recommendUserIds;
    }
}
