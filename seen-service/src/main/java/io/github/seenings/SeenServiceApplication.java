package io.github.seenings;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@Slf4j
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class SeenServiceApplication {
    static void main(String[] args) {
        SpringApplication.run(SeenServiceApplication.class, args);
    }
}
