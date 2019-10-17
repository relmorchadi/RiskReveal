package com.scor.rr;

import com.scor.rr.repository.PltHeaderRepository;
import com.scor.rr.repository.PltUserTagRepository;
import com.scor.rr.repository.UserRepository;
import com.scor.rr.repository.UserTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class RiskRevealApplication extends SpringBootServletInitializer implements CommandLineRunner {

	@Autowired
	PltUserTagRepository pltUserTagRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserTagRepository userTagRepository;

	@Autowired
	PltHeaderRepository pltHeaderRepository;


	public static void main(String[] args) {
		SpringApplication.run(RiskRevealApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {
		/*User u = userRepository.findById(1).orElse(null);*/

		/*UserTag userTag = userTagRepository.findById(7).orElse(null);

		PltHeader plt = pltHeaderRepository.findById("SPLTH-000735484").orElse(null);*/

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
