package io.github.seenings.recommend.http;

import io.github.seenings.sys.constant.ServiceNameConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static io.github.seenings.sys.constant.SeenConstant.FEIGN_VERSION;

/**
 * HttpMiddleUserRecommendService
 *
 * @author chixuehui
 * @since 2022-10-23
 */
@FeignClient(name = ServiceNameConstant.SERVICE_SEEN_MIDDLE, contextId = "HttpMiddleUserRecommendService", path = FEIGN_VERSION + "recommend/middle-user-recommend")
public interface HttpMiddleUserRecommendService {
    @PostMapping("have-user-id")
    Set<Long> haveUserId(@RequestParam("userId") Long userId, @RequestBody Set<Long> recommendUserIds);

    @PostMapping("user-id-to-recommend-user-id")
    Map<Long, List<Long>> userIdToRecommendUserId(@RequestBody Set<Long> userIds, @RequestParam("date") String date);

    @PostMapping("set")
    int set(@RequestParam("userId") Long userId, @RequestParam("date") String date, @RequestBody List<Long> recommendUserIds);
}
