package io.github.seenings.sys.config;

import io.github.seenings.sys.constant.ServiceNameConstant;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

/**
 * 微服务配置
 */
@Slf4j
@AutoConfiguration
@AllArgsConstructor
public class MicroAutoConfig {
    /**
     * 客户端
     */
    private RestClient.Builder restClientBuilder;

    /**
     * 币的代理
     *
     * @return 代理
     */
    @Bean
    public HttpServiceProxyFactory httpServiceProxyFactoryBySeenCoin() {
        RestClient restClient = restClientBuilder.baseUrl("http://" + ServiceNameConstant.SERVICE_SEEN_COIN).build();
        return HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient)).build();
    }

    /*
     * 用户服务的代理
     *
     * @return 代理
     */
    @Bean
    public HttpServiceProxyFactory httpServiceProxyFactoryBySeenUser() {
        RestClient restClient = restClientBuilder.baseUrl("http://" + ServiceNameConstant.SERVICE_SEEN_USER).build();
        return HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient)).build();
    }
}
