package io.github.seenings.sys.config;

import java.util.Set;

import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.github.seenings.sys.constant.PublicConstant;
import io.github.seenings.sys.interceptor.TokenInterceptor;

/**
 * 网络控制的配置
 */
@AutoConfiguration
@AllArgsConstructor
public class SeenWebMvcConfig implements WebMvcConfigurer {

    private SeenConfig seenConfig;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        var pathPatterns = Set
                .of(PublicConstant.REST, PublicConstant.PHOTO_VERSION, PublicConstant.VOICE_VERSION)
                .stream().map(n -> n + "**").toArray(String[]::new);
        registry.addInterceptor(new TokenInterceptor())
                .addPathPatterns(pathPatterns);
        WebMvcConfigurer.super.addInterceptors(registry);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 配置静态资源处理
        registry.addResourceHandler(PublicConstant.PHOTO_VERSION + "**")
                .addResourceLocations("file://" + seenConfig.getPathConfig().getPhotoPath());
        registry.addResourceHandler(PublicConstant.VOICE_VERSION + "**")
                .addResourceLocations("file://" + seenConfig.getPathConfig().getVoicePath());
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }
}
