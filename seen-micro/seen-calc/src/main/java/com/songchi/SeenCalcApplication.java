package com.songchi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * SeenCalcApplication
 *
 * @author chixuehui
 * @since 2023-05-21
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class SeenCalcApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeenCalcApplication.class, args);
    }
}
