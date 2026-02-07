package io.github.seenings.sys.config;

import io.github.seenings.recommend.http.HttpMiddleUserRecommendService;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

/**
 * 中间结果
 */
@AutoConfiguration
@AllArgsConstructor
public class MiddleAutoConfig {
    /**
     * 中间结果
     */
    private HttpServiceProxyFactory httpServiceProxyFactoryBySeenMiddle;

    /**
     * 用户推荐
     *
     * @return 接口实例
     */
    @Bean
    public HttpMiddleUserRecommendService httpMiddleUserRecommendService() {
        return httpServiceProxyFactoryBySeenMiddle.createClient(HttpMiddleUserRecommendService.class);
    }
}
