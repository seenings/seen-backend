package com.songchi.seen.sys.config;

import java.nio.charset.StandardCharsets;
import java.util.Set;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.UrlPathHelper;

import com.songchi.seen.sys.constant.PublicConstant;
import com.songchi.seen.sys.interceptor.TokenInterceptor;

import jakarta.servlet.MultipartConfigElement;

/**
 * @author chixuehui
 *         2021-05-09
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

    /* 更改程序映射请求路径默认策略 */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        UrlPathHelper urlPathHelper = new UrlPathHelper();
        urlPathHelper.setUrlDecode(false);
        urlPathHelper.setDefaultEncoding(StandardCharsets.UTF_8.name());
        configurer.setUrlPathHelper(urlPathHelper);
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {

        MultipartConfigFactory multipartConfigFactory = new MultipartConfigFactory();
        multipartConfigFactory.setMaxFileSize(DataSize.ofMegabytes(100));
        multipartConfigFactory.setMaxRequestSize(DataSize.ofMegabytes(100));
        return multipartConfigFactory.createMultipartConfig();
    }
}
