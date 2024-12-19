package com.songchi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 *  启动类
 */
@EnableEurekaServer
@SpringBootApplication
public class SeenRegistrationCenterApplication {
    /**
     * 主入口
     * @param args  命令行参数
     */
    public static void main(String[] args) {
        SpringApplication.run(SeenRegistrationCenterApplication.class, args);
    }

}
