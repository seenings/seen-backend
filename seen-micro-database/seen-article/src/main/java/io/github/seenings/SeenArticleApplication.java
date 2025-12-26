package io.github.seenings;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@EnableDiscoveryClient
@SpringBootApplication
public class SeenArticleApplication {

	static void main(String[] args) {
		SpringApplication.run(SeenArticleApplication.class, args);
	}

}
