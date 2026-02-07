package io.github.seenings.recommend.controller;

import io.github.seenings.recommend.http.HttpMiddleUserRecommendService;
import io.github.seenings.recommend.service.impl.MiddleUserRecommendService;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * MiddleUserRecommendController
 *
 * @author chixuehui
 * @since 2022-10-23
 */
@RestController
@AllArgsConstructor
public class MiddleUserRecommendController implements HttpMiddleUserRecommendService {

    @Resource
    private MiddleUserRecommendService middleUserRecommendService;

    @Override
    public Set<Long> haveUserId(Long userId, Set<Long> recommendUserIds) {
        return middleUserRecommendService.haveUserId(userId, recommendUserIds);
    }

    @Override
    public Map<Long, List<Long>> userIdToRecommendUserId(Set<Long> userIds, String date) {
        return middleUserRecommendService.userIdToRecommendUserId(userIds, date);
    }

    @Override
    public int set(Long userId,
                   String date, List<Long> recommendUserIds) {
        return middleUserRecommendService.set(userId, date, recommendUserIds);
    }
}
