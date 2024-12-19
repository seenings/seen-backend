package com.songchi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class SeenUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeenUserApplication.class, args);
    }
}
