package com.songchi.seen.recommend.http;

import com.songchi.seen.sys.constant.ServiceNameConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.songchi.seen.sys.constant.SeenConstant.FEIGN_VERSION;

/**
 * HttpMiddleUserRecommendService
 *
 * @author chixuehui
 * @since 2022-10-23
 */
@FeignClient(
        name = ServiceNameConstant.SERVICE_SEEN_MIDDLE,
        contextId = "HttpMiddleUserRecommendService",
        path = FEIGN_VERSION + "recommend/middle-user-recommend")
public interface HttpMiddleUserRecommendService {
    @PostMapping("have-user-id")
    Set<Integer> haveUserId(@RequestParam("userId") Integer userId, @RequestBody Set<Integer> recommendUserIds);

    @PostMapping("user-id-to-recommend-user-id")
    Map<Integer, List<Integer>> userIdToRecommendUserId(
            @RequestBody Set<Integer> userIds, @RequestParam("date") String date);

    @PostMapping("set")
    int set(
            @RequestParam("userId") Integer userId,
            @RequestParam("date") String date,
            @RequestBody List<Integer> recommendUserIds);
}
