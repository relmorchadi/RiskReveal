package com.scor.rr;

import com.scor.rr.ws.CatAnalysisWebService;
import com.scor.rr.ws.impl.CatAnalysisWebServiceImpl;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@Configuration
@ImportResource({ "classpath:META-INF/cxf/cxf.xml" })
public class RiskRevealApplication extends SpringBootServletInitializer implements CommandLineRunner {


	public static void main(String[] args) {
		SpringApplication.run(RiskRevealApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {
	}

	@Autowired
	private ApplicationContext applicationContext;

	@Bean
	public ServletRegistrationBean servletRegistrationBean(ApplicationContext context) {
		return new ServletRegistrationBean(new CXFServlet(), "/ws/*");
	}

	@Autowired
	private CatAnalysisWebServiceImpl catAnalysisWebService;

	@Bean
	public EndpointImpl catAnalysisWebService() {
		Bus bus = (Bus) applicationContext.getBean(Bus.DEFAULT_BUS_ID);
		EndpointImpl endpoint = new EndpointImpl(bus, catAnalysisWebService);
		endpoint.publish("/CatAnalysisWebService");
		return endpoint;
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(RiskRevealApplication.class);
	}
}
