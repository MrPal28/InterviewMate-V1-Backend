package com.interviewmate.resumeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.interviewmate.resumeservice.client")
public class ResumeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResumeServiceApplication.class, args);
	}

}
