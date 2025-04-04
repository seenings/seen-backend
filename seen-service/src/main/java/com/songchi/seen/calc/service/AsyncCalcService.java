package com.songchi.seen.calc.service;

import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * CalcService
 *
 * @author chixuehui
 * @since 2022-10-23
 */
public interface AsyncCalcService {
    @Async
    CompletableFuture<List<Long>> userIdToRecommendUserId(Long userId, String date);
}
