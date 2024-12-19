package com.songchi.seen.sys.config;

import com.songchi.seen.common.model.EnvConfig;
import com.songchi.seen.common.model.PathConfig;
import com.songchi.seen.sys.model.SeenMinioConfig;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

import static com.songchi.seen.sys.constant.PublicConstant.SMALL_SEEN;

/**
 * 自定义配置
 *
 * @author chixuehui
 * @since 2022-02-19
 */
@Data
@Configuration
@ConfigurationProperties(SMALL_SEEN)
public class SeenConfig {

    /**
     * 路径配置
     */
    @NestedConfigurationProperty
    private PathConfig pathConfig = new PathConfig();
    /**
     * 环境配置
     */
    @NestedConfigurationProperty
    private EnvConfig envConfig = new EnvConfig();
    /**
     * 分布式存储配置
     */
    @NestedConfigurationProperty
    private SeenMinioConfig seenMinioConfig = new SeenMinioConfig();
}

