package com.songchi.seen;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 */
@Slf4j
@SpringBootApplication
public class SeenGatewayApplication {
    /**
     * 应用入口
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        SpringApplication.run(SeenGatewayApplication.class, args);
        System.getProperties().forEach((key, value) -> log.info(key + "=" + value));
        log.info("应用启动成功。");
    }

}
