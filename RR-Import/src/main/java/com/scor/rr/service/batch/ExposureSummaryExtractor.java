package com.scor.rr.service.batch;

import com.scor.rr.domain.ProjectImportRun;
import com.scor.rr.repository.*;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@StepScope
public class ExposureSummaryExtractor {


    @Autowired
    private ExposureViewDefinitionRepository exposureViewDefinitionRepository;

    @Autowired
    private ExposureViewVersionRepository exposureViewVersionRepository;

    @Autowired
    private ExposureViewQueryRepository exposureViewExtractQueryRepository;

    @Autowired
    private GlobalExposureViewRepository globalExposureViewRepository;

    @Autowired
    private ProjectImportRunRepository projectImportRunRepository;

    @Value("#{jobParameters['projectId']}")
    protected String projectId;

    public RepeatStatus extract() {

        try {
            List<ProjectImportRun> projectImportRunList = projectImportRunRepository.findByProjectProjectId(Long.valueOf(projectId));
            ProjectImportRun projectImportRun = projectImportRunRepository.findByProjectProjectIdAndRunId(Long.valueOf(projectId), projectImportRunList.size());


            return RepeatStatus.FINISHED;
        } catch (Exception ex) {
            ex.printStackTrace();
            return RepeatStatus.CONTINUABLE;
        }

    }
}
