package io.github.seenings.sys.config;

import io.github.seenings.introduce.http.HttpUserIntroducePhotoService;
import io.github.seenings.introduce.http.HttpUserIntroduceService;
import io.github.seenings.text.http.HttpTagService;
import io.github.seenings.text.http.HttpTextService;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

/**
 * 计算的配置
 */
@AutoConfiguration
@AllArgsConstructor
public class ArticleAutoConfig {
    /*
     * 文章服务
     *
     */
    private HttpServiceProxyFactory httpServiceProxyFactoryBySeenArticle;

    /**
     * 标签
     *
     * @return 接口
     */
    @Bean
    public HttpTagService httpTagService() {
        return httpServiceProxyFactoryBySeenArticle.createClient(HttpTagService.class);
    }
    /**
     * 文本
     *
     * @return 接口
     */
    @Bean
    public HttpTextService httpTextService() {
        return httpServiceProxyFactoryBySeenArticle.createClient(HttpTextService.class);
    }
    /**
     * 用户介绍
     *
     * @return 接口
     */
    @Bean
    public HttpUserIntroduceService httpUserIntroduceService() {
        return httpServiceProxyFactoryBySeenArticle.createClient(HttpUserIntroduceService.class);
    }
    /**
     * 用户照片介绍
     *
     * @return 接口
     */
    @Bean
    public HttpUserIntroducePhotoService httpUserIntroducePhotoService() {
        return httpServiceProxyFactoryBySeenArticle.createClient(HttpUserIntroducePhotoService.class);
    }
}
