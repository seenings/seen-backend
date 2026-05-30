package io.github.seenings.sys.config;

import io.github.seenings.info.http.*;
import io.github.seenings.thumb.http.HttpFocusUserService;
import io.github.seenings.thumb.http.HttpThumbUserService;
import io.github.seenings.work.http.HttpUserWorkService;
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
    /**
     * 昵称
     *
     * @return 接口实例
     */
    @Bean
    public HttpUserAliasNameService httpUserAliasNameService() {
        return httpServiceProxyFactoryBySeenUser.createClient(HttpUserAliasNameService.class);
    }/**
     * 用户身高
     *
     * @return 接口实例
     */
    @Bean
    public HttpUserStatureService httpUserStatureService() {
        return httpServiceProxyFactoryBySeenUser.createClient(HttpUserStatureService.class);
    }/**
     * 用户体重
     *
     * @return 接口实例
     */
    @Bean
    public HttpUserWeightService httpUserWeightService() {
        return httpServiceProxyFactoryBySeenUser.createClient(HttpUserWeightService.class);
    }/**
     * 工作
     *
     * @return 接口实例
     */
    @Bean
    public HttpUserWorkService httpUserWorkService() {
        return httpServiceProxyFactoryBySeenUser.createClient(HttpUserWorkService.class);
    }/**
     * 点赞
     *
     * @return 接口实例
     */
    @Bean
    public HttpThumbUserService httpThumbUserService() {
        return httpServiceProxyFactoryBySeenUser.createClient(HttpThumbUserService.class);
    }/**
     * 关注
     *
     * @return 接口实例
     */
    @Bean
    public HttpFocusUserService httpFocusUserService() {
        return httpServiceProxyFactoryBySeenUser.createClient(HttpFocusUserService.class);
    }/**
     * 婚姻情况
     *
     * @return 接口实例
     */
    @Bean
    public HttpUserMaritalService httpUserMaritalService() {
        return httpServiceProxyFactoryBySeenUser.createClient(HttpUserMaritalService.class);
    }/**
     * 认证
     *
     * @return 接口实例
     */
    @Bean
    public HttpUserAuthService httpUserAuthService() {
        return httpServiceProxyFactoryBySeenUser.createClient(HttpUserAuthService.class);
    }/**
     * 职位
     *
     * @return 接口实例
     */
    @Bean
    public HttpUserWorkPositionService httpUserWorkPositionService() {
        return httpServiceProxyFactoryBySeenUser.createClient(HttpUserWorkPositionService.class);
    }/**
     * 生日
     *
     * @return 接口实例
     */
    @Bean
    public HttpUserBirthdayService httpUserBirthdayService() {
        return httpServiceProxyFactoryBySeenUser.createClient(HttpUserBirthdayService.class);
    }/**
     * 职位
     *
     * @return 接口实例
     */
    @Bean
    public HttpWorkPositionService httpWorkPositionService() {
        return httpServiceProxyFactoryBySeenUser.createClient(HttpWorkPositionService.class);
    }/**
     * 出生地
     *
     * @return 接口实例
     */
    @Bean
    public HttpUserBirthPlaceService httpUserBirthPlaceService() {
        return httpServiceProxyFactoryBySeenUser.createClient(HttpUserBirthPlaceService.class);
    }
}
