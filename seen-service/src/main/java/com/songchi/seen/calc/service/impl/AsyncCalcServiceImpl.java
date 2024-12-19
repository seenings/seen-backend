package com.songchi.seen.calc.service.impl;

import com.songchi.seen.calc.http.HttpRecommendService;
import com.songchi.seen.calc.service.AsyncCalcService;
import com.songchi.seen.recommend.http.HttpMiddleUserRecommendService;
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
    public CompletableFuture<List<Integer>> userIdToRecommendUserId(Integer userId, String date) {
        // 生成
        List<Integer> recommendUserIds = httpRecommendService.createRecommendUser(userId);
        httpMiddleUserRecommendService.set(userId, date, recommendUserIds);
        return CompletableFuture.completedFuture(recommendUserIds);
    }
}
