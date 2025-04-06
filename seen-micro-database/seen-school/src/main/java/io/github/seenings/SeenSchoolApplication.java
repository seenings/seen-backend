package io.github.seenings;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class SeenSchoolApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeenSchoolApplication.class, args);
    }
}
