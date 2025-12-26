package io.github.seenings;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * SeenCalcApplication
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class SeenCalcApplication {

    static void main(String[] args) {
        SpringApplication.run(SeenCalcApplication.class, args);
    }
}
