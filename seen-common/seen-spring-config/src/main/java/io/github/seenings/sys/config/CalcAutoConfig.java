package io.github.seenings.sys.config;

import io.github.seenings.calc.http.HttpRecommendService;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

/**
 * 计算的配置
 */
@AutoConfiguration
@AllArgsConstructor
public class CalcAutoConfig {
    /*
     * 计算的代理
     *
     */
    private HttpServiceProxyFactory httpServiceProxyFactoryBySeenCalc;

    /**
     * 推荐
     *
     * @return 接口
     */
    @Bean
    public HttpRecommendService httpRecommendService() {
        return httpServiceProxyFactoryBySeenCalc.createClient(HttpRecommendService.class);
    }


}
