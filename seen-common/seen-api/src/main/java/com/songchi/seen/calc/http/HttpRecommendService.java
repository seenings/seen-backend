package com.songchi.seen.calc.http;

import com.songchi.seen.sys.constant.ServiceNameConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.songchi.seen.sys.constant.SeenConstant.FEIGN_VERSION;

/**
 * HttpRecommendService
 *
 * @author chixuehui
 * @since 2023-05-21
 */

@FeignClient(name = ServiceNameConstant.SERVICE_SEEN_CALC, contextId = "HttpRecommendService", path = FEIGN_VERSION + "calc/recommend")
public interface HttpRecommendService {
    /**
     * 为用户推荐用户
     * @param userId    用户ID
     * @return  被推荐的用户
     */
    @PostMapping("create-recommend-user")
    List<Integer> createRecommendUser(@RequestParam("userId") Integer userId);
}
