package com.scor.rr;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.util.Date;

@SpringBootApplication
public class RiskRevealApplication extends SpringBootServletInitializer implements CommandLineRunner {


	public static void main(String[] args) {
		SpringApplication.run(RiskRevealApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {
	}
}
