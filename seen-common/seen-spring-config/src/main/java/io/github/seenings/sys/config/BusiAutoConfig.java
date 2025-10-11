package io.github.seenings.sys.config;

import io.github.seenings.busi.controller.BusiController;
import io.github.seenings.busi.controller.BusiRegisterController;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

/**
 * 业务的配置
 */
@AutoConfiguration
@AllArgsConstructor
public class BusiAutoConfig {
    /**
     * 业务的代理
     */
    private HttpServiceProxyFactory httpServiceProxyFactoryBySeenBusi;

    /**
     * 用户注册
     *
     * @return 用户注册
     */
    @Bean
    public BusiRegisterController busiRegisterController() {
        return httpServiceProxyFactoryBySeenBusi.createClient(BusiRegisterController.class);
    }

    /**
     * 业务
     *
     * @return 业务
     */
    @Bean
    public BusiController busiController() {
        return httpServiceProxyFactoryBySeenBusi.createClient(BusiController.class);
    }

}
