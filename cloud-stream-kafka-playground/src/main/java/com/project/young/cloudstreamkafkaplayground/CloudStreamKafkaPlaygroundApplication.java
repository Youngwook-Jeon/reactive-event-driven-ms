package com.project.young.cloudstreamkafkaplayground;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.project.young.cloudstreamkafkaplayground.${sec}")
public class CloudStreamKafkaPlaygroundApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudStreamKafkaPlaygroundApplication.class, args);
	}

}
