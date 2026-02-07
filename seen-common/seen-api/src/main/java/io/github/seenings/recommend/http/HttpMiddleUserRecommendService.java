package io.github.seenings.recommend.http;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

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
@HttpExchange(FEIGN_VERSION + "recommend/middle-user-recommend")
public interface HttpMiddleUserRecommendService {
    @PostExchange("have-user-id")
    Set<Long> haveUserId(@RequestParam("userId") Long userId, @RequestBody Set<Long> recommendUserIds);

    @PostExchange("user-id-to-recommend-user-id")
    Map<Long, List<Long>> userIdToRecommendUserId(@RequestBody Set<Long> userIds, @RequestParam("date") String date);

    @PostExchange("set")
    int set(@RequestParam("userId") Long userId, @RequestParam("date") String date, @RequestBody List<Long> recommendUserIds);
}
