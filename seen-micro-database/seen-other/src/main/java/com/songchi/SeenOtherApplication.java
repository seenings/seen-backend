package com.songchi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@EnableDiscoveryClient
@SpringBootApplication
public class SeenOtherApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeenOtherApplication.class, args);
    }
}
