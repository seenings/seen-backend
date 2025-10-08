package io.github.seenings.sys.config;

import io.github.seenings.time.component.NowComponent;
import io.github.seenings.time.component.impl.NowComponentImpl;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;

/**
 * 自动加载配置
 */
@AutoConfiguration
public class SeenSpringAutoConfiguration {
    /**
     * 当前时间组件
     *
     * @return 当前时间组件
     */
    @Bean
    public NowComponent nowComponent() {
        return new NowComponentImpl();
    }


    /**
     * 客户端
     *
     * @return 客户端
     */
    @Bean
    @LoadBalanced
    public RestClient.Builder restClientBuilder() {
        return RestClient.builder();
    }
}
