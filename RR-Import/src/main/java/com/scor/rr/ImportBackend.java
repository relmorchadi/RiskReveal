package com.scor.rr;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaRepositories("com.scor.rr.repository")
@EnableBatchProcessing
@EnableTransactionManagement
public class ImportBackend extends SpringBootServletInitializer implements CommandLineRunner {


    public static void main(String[] args) {
        SpringApplication.run(ImportBackend.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        /*User u = userRepository.findByModelingResultDataSourceId(1).orElse(null);*/

		/*UserTag userTag = userTagRepository.findByModelingResultDataSourceId(7).orElse(null);

		PltHeader plt = pltHeaderRepository.findByModelingResultDataSourceId("SPLTH-000735484").orElse(null);*/

		/*UserTagPlt assignment = new UserTagPlt(userTag, plt);
		assignment.setAssignedAt(new Date());
		assignment.setAssigner(u);
		pltUserTagRepository.save(assignment);*/

		/*UserTagPlt assignment = pltUserTagRepository.findByTagAndPlt(userTag, plt);

		System.out.println(assignment);
		pltUserTagRepository.delete(assignment);*/

        //pltUserTagRepository.deleteByUserTagPltPkTagAndPlt(userTag, plt);
    }
}
