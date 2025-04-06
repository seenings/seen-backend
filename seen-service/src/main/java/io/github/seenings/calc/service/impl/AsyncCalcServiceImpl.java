package io.github.seenings.calc.service.impl;

import io.github.seenings.calc.http.HttpRecommendService;
import io.github.seenings.calc.service.AsyncCalcService;
import io.github.seenings.recommend.http.HttpMiddleUserRecommendService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * CalcServiceImpl
 *
 * @author chixuehui
 * @since 2022-10-23
 */
@Service
@AllArgsConstructor
public class AsyncCalcServiceImpl implements AsyncCalcService {

    private HttpRecommendService httpRecommendService;

    private HttpMiddleUserRecommendService httpMiddleUserRecommendService;

    @Async
    @Override
    public CompletableFuture<List<Long>> userIdToRecommendUserId(Long userId, String date) {
        // 生成
        List<Long> recommendUserIds = httpRecommendService.createRecommendUser(userId);
        httpMiddleUserRecommendService.set(userId, date, recommendUserIds);
        return CompletableFuture.completedFuture(recommendUserIds);
    }
}
