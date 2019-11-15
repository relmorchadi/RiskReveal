package com.scor.rr.service.batch;

import com.scor.rr.domain.*;
import com.scor.rr.repository.*;
import com.scor.rr.service.RmsService;
import com.scor.rr.service.state.TransformationPackage;
import org.apache.commons.collections.keyvalue.MultiKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@StepScope
public class ExposureSummaryExtractor {

    private static final Logger log = LoggerFactory.getLogger(ExposureSummaryExtractor.class);

    @Autowired
    private ExposureViewDefinitionRepository exposureViewDefinitionRepository;

    @Autowired
    private ExposureViewVersionRepository exposureViewVersionRepository;

    @Autowired
    private ExposureViewQueryRepository exposureViewQueryRepository;

    @Autowired
    private GlobalExposureViewRepository globalExposureViewRepository;

    @Autowired
    private ProjectImportRunRepository projectImportRunRepository;

    @Autowired
    private ExposureViewRepository exposureViewRepository;

    @Autowired
    private TransformationPackage transformationPackage;

    @Autowired
    private RmsService rmsService;

    @Value("#{jobParameters['projectId']}")
    protected String projectId;

    @Value("#{jobParameters['division']}")
    protected String division;

    @Value("#{jobParameters['periodBasis']}")
    protected String periodBasis;

    @Value("#{jobParameters['importSequence']}")
    protected Long importSequence;

    private MultiKey createEdmKey(String instance, Long edmId, String edmName) {
        return new MultiKey(instance, edmId, edmName);
    }

    public RepeatStatus extract() {

        try {
            List<ProjectImportRun> projectImportRunList = projectImportRunRepository.findByProjectProjectId(Long.valueOf(projectId));
            ProjectImportRun projectImportRun = projectImportRunRepository.findByProjectProjectIdAndRunId(Long.valueOf(projectId), projectImportRunList.size());

            List<ModelPortfolio> modelPortfolios = transformationPackage.getModelPortfolios();

            if (modelPortfolios != null && !modelPortfolios.isEmpty()) {
                Map<MultiKey, List<String>> portfoliosPerEdm = new HashMap<>();

                modelPortfolios.forEach(modelPortfolio -> {
                    MultiKey edmKey = createEdmKey(modelPortfolio.getSourceModellingSystemInstance(),modelPortfolio.getDataSourceId(), modelPortfolio.getDataSourceName());
                    Long portfolioId = modelPortfolio.getPortfolioId();
                    String conformedCcy = modelPortfolio.getCurrency();
                    if(portfoliosPerEdm.get(edmKey) != null)
                        portfoliosPerEdm.get(edmKey).add(portfolioId + "~" + conformedCcy);
                    else
                    portfoliosPerEdm.put(edmKey, new ArrayList<>(Collections.singleton(portfolioId + "~" + conformedCcy)));
                });

                ExposureView defaultExposureView = exposureViewRepository.findByDefaultView(true);
                if (defaultExposureView != null) {
                    GlobalExposureView globalExposureView = new GlobalExposureView();
                    globalExposureView.setProjectId(Long.valueOf(projectId));
                    globalExposureView.setName(defaultExposureView.getName());
                    if (division != null)
                        globalExposureView.setDivisionNumber(Integer.valueOf(division));
                    //TODO : FIX ME LATER
                    globalExposureView.setPeriodBasisId(null);
                    if (importSequence != null)
                        globalExposureView.setVersion(importSequence.intValue());

                    globalExposureViewRepository.save(globalExposureView);
                    List<ExposureViewDefinition> exposureViewDefinitions = exposureViewDefinitionRepository.findByExposureView(defaultExposureView);

                    for (Map.Entry<MultiKey, List<String>> entry : portfoliosPerEdm.entrySet()) {
                        MultiKey edm = entry.getKey();
                        Integer runId = rmsService.createEDMPortfolioContext((String)edm.getKey(0),
                                (Long) edm.getKey(1),
                                (String)edm.getKey(2),
                                entry.getValue(),
                                null);
                        if (runId != null) {
                            Map<String, Object> params = new HashMap<>();
                            params.put("edm_id", edm.getKey(1));
                            params.put("edm_name", edm.getKey(2));
                            params.put("run_id", runId);
                            exposureViewDefinitions.forEach(exposureViewDefinition -> {
                                ExposureViewVersion exposureViewVersion = exposureViewVersionRepository.findByExposureViewDefinitionAndCurrent(exposureViewDefinition, true);
                                if (exposureViewVersion != null) {
                                    ExposureViewQuery exposureViewQuery = exposureViewQueryRepository.findByExposureViewVersion(exposureViewVersion);
                                    NamedParameterJdbcTemplate template = rmsService.createTemplate((String)edm.getKey(0));
                                    template.query(exposureViewQuery.getQuery(), params, );
                                }
                            });
                            rmsService.removeEDMPortfolioContext((String)edm.getKey(0), runId);
                        } else {
                            log.error("Error: runId is null");
                        }
                    }


                } else {
                    log.warn("No default view was found");
                }
            } else {
                log.info("No model portfolio selected");
            }
            return RepeatStatus.FINISHED;
        } catch (Exception ex) {
            ex.printStackTrace();
            return RepeatStatus.CONTINUABLE;
        }

    }


}
