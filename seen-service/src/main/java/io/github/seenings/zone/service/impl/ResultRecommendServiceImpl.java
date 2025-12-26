package io.github.seenings.zone.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import io.github.seenings.calc.http.HttpRecommendService;
import io.github.seenings.calc.service.AsyncCalcService;
import io.github.seenings.core.util.CollUtil;
import io.github.seenings.recommend.http.HttpMiddleUserRecommendService;
import io.github.seenings.zone.service.ResultRecommendService;
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
        if (!CollUtil.isNotEmpty(recommendUserIds)) {
            // 生成
            recommendUserIds = httpRecommendService.createRecommendUser(userId);
            httpMiddleUserRecommendService.set(userId, date, recommendUserIds);
        }
        return recommendUserIds;
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
