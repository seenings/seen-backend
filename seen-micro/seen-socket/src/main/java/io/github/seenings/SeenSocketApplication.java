package io.github.seenings;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@EnableFeignClients
@SpringBootApplication
public class SeenSocketApplication {

    static void main(String[] args) {
        SpringApplication.run(SeenSocketApplication.class, args);
    }

    // 将ServerEndpointExporter 注册为一个spring的bean
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
