package com.songchi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.songchi","io.github.seenings"})
@MapperScan(basePackages = {"io.github.seenings","com.songchi"})
public class SeenCoinApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeenCoinApplication.class, args);
    }
}
