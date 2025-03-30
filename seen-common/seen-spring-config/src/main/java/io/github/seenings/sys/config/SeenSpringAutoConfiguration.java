package io.github.seenings.sys.config;

import io.github.seenings.time.component.NowComponent;
import io.github.seenings.time.component.impl.NowComponentImpl;
import org.springframework.context.annotation.Bean;

/**
 * 自动加载配置
 */
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
}
