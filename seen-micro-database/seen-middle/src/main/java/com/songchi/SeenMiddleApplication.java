package com.songchi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class SeenMiddleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeenMiddleApplication.class, args);
    }
}
