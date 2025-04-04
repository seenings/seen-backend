package com.songchi.seen.recommend.controller;

import com.songchi.seen.recommend.http.HttpMiddleUserRecommendService;
import com.songchi.seen.recommend.service.impl.MiddleUserRecommendService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.songchi.seen.sys.constant.SeenConstant.FEIGN_VERSION;

/**
 * MiddleUserRecommendController
 *
 * @author chixuehui
 * @since 2022-10-23
 */
@RestController
@RequestMapping(FEIGN_VERSION + "recommend/middle-user-recommend")
public class MiddleUserRecommendController implements HttpMiddleUserRecommendService {

    @Resource
    private MiddleUserRecommendService middleUserRecommendService;

    @Override
    @PostMapping("have-user-id")
    public Set<Long> haveUserId(@RequestParam("userId") Long userId, @RequestBody Set<Long> recommendUserIds) {
        return middleUserRecommendService.haveUserId(userId, recommendUserIds);
    }

    @Override
    @PostMapping("user-id-to-recommend-user-id")
    public Map<Long, List<Long>> userIdToRecommendUserId(
            @RequestBody Set<Long> userIds, @RequestParam("date") String date) {
        return middleUserRecommendService.userIdToRecommendUserId(userIds, date);
    }

    @Override
    @PostMapping("set")
    public int set(
            @RequestParam("userId") Long userId,
            @RequestParam("date") String date,
            @RequestBody List<Long> recommendUserIds) {
        return middleUserRecommendService.set(userId, date, recommendUserIds);
    }
}
