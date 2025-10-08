package io.github.seenings.sys.config;

import io.github.seenings.info.http.UserController;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

/**
 * 币的配置
 */
@Slf4j
@AutoConfiguration
@AllArgsConstructor
public class UserAutoConfig {
    /**
     * 用户服务的代理
     */
    private HttpServiceProxyFactory httpServiceProxyFactoryBySeenUser;

    /**
     * 用户信息
     *
     * @return 用户信息
     */
    @Bean
    public UserController userController() {
        return httpServiceProxyFactoryBySeenUser.createClient(UserController.class);
    }
}
