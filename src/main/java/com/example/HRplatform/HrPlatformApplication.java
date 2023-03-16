package com.example.HRplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
@EntityScan(basePackages = {"com.example.HRplatform.model"})
public class HrPlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(HrPlatformApplication.class, args);
	}

}
