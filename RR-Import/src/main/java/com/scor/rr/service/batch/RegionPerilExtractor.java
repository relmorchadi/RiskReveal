package com.scor.rr.service.batch;

import com.scor.rr.domain.workspace.Project;
import com.scor.rr.repository.ProjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@StepScope
public class RegionPerilExtractor {

    private static final Logger log = LoggerFactory.getLogger(RegionPerilExtractor.class);

    @Autowired
    private ProjectRepository projectRepository;

    @Value("${jobParameters[projectId]}")
    private long projectId;

    public void loadRegionPeril() {
        log.debug("Start loading region perils");
        Optional<Project> projectOptional = projectRepository.findById(projectId);
        Project project;

        if (projectOptional.isPresent())
            project = projectOptional.get();
        else {
            log.debug("project not found");
            throw new IllegalArgumentException("invalid project id");
        }


    }
}
