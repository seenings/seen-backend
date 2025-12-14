package io.github.seenings.sys.config;

import java.util.Set;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.github.seenings.sys.constant.PublicConstant;
import io.github.seenings.sys.interceptor.TokenInterceptor;

/**
 * @author chixuehui
 * 2021-05-09
 */
@Configuration
public class SeenWebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        var pathPatterns = Set
                .of(PublicConstant.SSE_VERSION)
                .stream().map(n -> n + "**").toArray(String[]::new);
        registry.addInterceptor(new TokenInterceptor())
                .addPathPatterns(pathPatterns);
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
