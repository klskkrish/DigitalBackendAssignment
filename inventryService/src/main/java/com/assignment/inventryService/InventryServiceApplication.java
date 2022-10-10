package com.assignment.inventryService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication(scanBasePackages =  {"com.assignment.inventryService"})
@EnableEurekaClient
public class InventryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventryServiceApplication.class, args);
	}
}
