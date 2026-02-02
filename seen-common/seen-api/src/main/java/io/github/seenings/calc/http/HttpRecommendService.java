package io.github.seenings.calc.http;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.List;

import static io.github.seenings.sys.constant.SeenConstant.FEIGN_VERSION;

/// 推荐
@HttpExchange(FEIGN_VERSION + "calc/recommend")
public interface HttpRecommendService {
    /**
     * 为用户推荐用户
     *
     * @param userId 用户ID
     * @return 被推荐的用户
     */
    @PostExchange("create-recommend-user")
    List<Long> createRecommendUser(@RequestParam("userId") Long userId);
}
