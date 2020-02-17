package com.scor.rr.proxy;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@EnableJpaRepositories("com.scor.rr.repository")
@EntityScan("com.scor.rr.domain")
@EnableTransactionManagement
public class RiskRevealProxy extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(RiskRevealProxy.class, args);
    }

}
