package com.seen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @author chixuehui
 */
@EnableConfigServer
@SpringBootApplication
public class SeenConfigServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeenConfigServerApplication.class, args);
    }

}
