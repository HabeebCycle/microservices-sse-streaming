package com.habeebcycle.microservice.servicec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.habeebcycle")
public class ServiceCApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceCApplication.class, args);
	}

}
