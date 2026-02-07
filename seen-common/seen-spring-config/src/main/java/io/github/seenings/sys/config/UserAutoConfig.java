package io.github.seenings.sys.config;

import io.github.seenings.info.http.HttpUserCurrentResidenceService;
import io.github.seenings.info.http.HttpUserMainPhotoService;
import io.github.seenings.info.http.HttpUserSexService;
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

    /**
     * 当前居住地
     *
     * @return 接口实例
     */
    @Bean
    public HttpUserCurrentResidenceService httpUserCurrentResidenceService() {
        return httpServiceProxyFactoryBySeenUser.createClient(HttpUserCurrentResidenceService.class);
    }

    /**
     * 头像
     *
     * @return 接口实例
     */
    @Bean
    public HttpUserMainPhotoService httpUserMainPhotoService() {
        return httpServiceProxyFactoryBySeenUser.createClient(HttpUserMainPhotoService.class);
    }

    /**
     * 用户性别
     *
     * @return 接口实例
     */
    @Bean
    public HttpUserSexService httpUserSexService() {
        return httpServiceProxyFactoryBySeenUser.createClient(HttpUserSexService.class);
    }
}
