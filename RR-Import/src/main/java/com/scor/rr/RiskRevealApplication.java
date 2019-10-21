package com.scor.rr;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.scor.rr.repository")
@EnableBatchProcessing
public class RiskRevealApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(RiskRevealApplication.class, args);
	}

}
