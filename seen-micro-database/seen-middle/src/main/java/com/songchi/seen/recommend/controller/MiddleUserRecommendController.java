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
    public Set<Integer> haveUserId(@RequestParam("userId") Integer userId, @RequestBody Set<Integer> recommendUserIds) {
        return middleUserRecommendService.haveUserId(userId, recommendUserIds);
    }

    @Override
    @PostMapping("user-id-to-recommend-user-id")
    public Map<Integer, List<Integer>> userIdToRecommendUserId(
            @RequestBody Set<Integer> userIds, @RequestParam("date") String date) {
        return middleUserRecommendService.userIdToRecommendUserId(userIds, date);
    }

    @Override
    @PostMapping("set")
    public int set(
            @RequestParam("userId") Integer userId,
            @RequestParam("date") String date,
            @RequestBody List<Integer> recommendUserIds) {
        return middleUserRecommendService.set(userId, date, recommendUserIds);
    }
}
