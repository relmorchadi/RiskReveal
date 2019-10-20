package batch;

import com.scor.rr.domain.entities.ihub.RepresentationDataset;
import com.scor.rr.domain.entities.ihub.SelectedAssociationBag;
import com.scor.rr.domain.entities.ihub.SourceResult;
import com.scor.rr.domain.entities.references.User;
import com.scor.rr.domain.entities.rms.AnalysisFinancialPerspective;
import com.scor.rr.domain.entities.rms.RmsProjectImportConfig;
import com.scor.rr.domain.entities.workspace.Portfolio;
import com.scor.rr.domain.entities.workspace.Project;
import com.scor.rr.repository.ihub.RepresentationDatasetRepository;
import com.scor.rr.repository.ihub.SourceResultRepository;
import com.scor.rr.repository.references.UserRepository;
import com.scor.rr.repository.rms.RmsProjectImportConfigRepository;
import com.scor.rr.repository.workspace.ProjectRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;


@RunWith(SpringRunner.class)
@ContextConfiguration(locations = {"classpath:importLossData.xml"})
public class SpringBatchExecutorTest {

    private static final Logger log = LoggerFactory.getLogger(SpringBatchExecutorTest.class);

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    JobRegistry jobRegistry;

    // old code ri
//    @Autowired
//    ProjectDatasourceAssociationRepository projectDatasourceAssociationRepository;

    // new code ri
    @Autowired
    RmsProjectImportConfigRepository rmsProjectImportConfigRepository;

    @Autowired
    RepresentationDatasetRepository representationDatasetRepository;

    @Autowired
    SourceResultRepository sourceResultRepository;

    @Test
    public void batchTest() {
        try {
            // REMEMBER TO REMOVE LIMIT OF ANALYSES FOR THE CASE BUILDERFORTARGETRAP129 IN RMSELTPARTITIONERIMPL
            JobParametersBuilder builderForTargetRap94 = new JobParametersBuilder()
                    .addString("catReqId", "CAR-02345")
                    .addString("edm", "SG1604_JPN_TMNF_CatXLPAEQ_E")
                    .addLong("edmId", 3058L)
                    .addString("rdm", "SG1604_JPN_TMNF_CatXLPAEQ_R")
                    .addLong("rdmId", 3059L)
                    .addString("portfolio", "KF_Combined")
                    .addString("division", "division")
                    .addString("periodBasis", "FullTerm")
                    .addLong("version", 0l)
                    .addString("fpELT", "GR")
                    .addString("fpStats", "GU")
                    .addString("instanceId", "RL152")
                    .addString("correlationId", UUID.randomUUID().toString())
                    .addDate("runDate", new Date());

            // 195 ANALYSES
            // TO LIMIT, CHECK FOR PROFILE KEY RMS_MVXX.X_NOWS
            JobParametersBuilder builderForTargetRap129 = new JobParametersBuilder()
                    .addString("catReqId", "CAR-02345")
                    .addString("edm", "CG1601_EUR_WSportfolio_E")
                    .addLong("edmId", 256L)
                    .addString("rdm", "CG1601_EUR_WSportfolio_R")
                    .addLong("rdmId", 257L)
                    .addString("portfolio", "KF_Combined")
                    .addString("division", "division")
                    .addString("periodBasis", "FullTerm")
                    .addLong("version", 0l)
                    .addString("fpELT", "GR")
                    .addString("fpStats", "GU")
                    .addString("instanceId", "RL152")
                    .addString("correlationId", UUID.randomUUID().toString())
                    .addDate("runDate", new Date());

            Job job = jobRegistry.getJob("importLossData");
            JobExecution execution = jobLauncher.run(job, builderForTargetRap94.toJobParameters());
            Long jobExecutionId = execution.getId();

            log.info("Exit Status : " + execution.getStatus());
            log.info("Exit Status : " + execution.getAllFailureExceptions());

        } catch (Exception e) {
            log.error("", e);
        }
        log.info("Done");
    }


    // old code ri
//    @Test
//    public void importBatchTest() {
//        //        one shot test
////        String pdaId = "PDA_000000705";
////        String userId = "ABRO";
////        String projectId = "P00000019";
////        String sourceResultIds = "P00000022_AV_000316_5109_9";
////        String portfolioIds = "P00000022_AV_000316_5107_6";
//
////        multishot test
//        String pdaId = "PDA_000000705";
//        String userId = "ABRO";
//        String regionPeril = "USWT";
//        String fpCode = "GR";
//
//        ProjectDatasourceAssociation projectDatasourceAssociation = projectDatasourceAssociationRepository.findOne(pdaId);
//
//        Set<Portfolio> portfolios = new HashSet<>();
//        Set<SourceResult> sourceResults = new HashSet<>();
//
//        if (projectDatasourceAssociation != null) {
//            if (projectDatasourceAssociation.getSelectedAssociationBags().size() > 0) {
//
//                Set<String> uniquePortfolios = new HashSet<>();
//                for (SelectedAssociationBag sab : projectDatasourceAssociation.getSelectedAssociationBags()) {
//                    if (sab.getRepresentationDatasets().size() > 0) {
//                        for (RepresentationDataset rp : sab.getRepresentationDatasets()) {
//                            for (Portfolio port : rp.getRepresentedPortfolios()) {
//                                if (!uniquePortfolios.contains(port.getId())) {
//                                    portfolios.add(port);
//                                }
//                                uniquePortfolios.add(port.getId());
//                            }
//                            sourceResults.add(rp.getRepresentedSourceResults().get(0));
//                        }
//                    }
//                }
//            }
//        }
//
//        for (SourceResult sourceResult : sourceResults) {
//            sourceResult.setFinalizedRegionPeril(regionPeril);
//            sourceResult.setFinancialPerspective(new AnalysisFinancialPerspective(fpCode));
//            sourceResultRepository.save(sourceResult);
//        }
//
//        String projectId = projectDatasourceAssociation.getProject().getId();
//
//        StringBuilder sbuilder = new StringBuilder();
//        for (SourceResult sourceResult : sourceResults) {
//            sbuilder.append(sourceResult.getId()).append(";");
//        }
//        sbuilder.deleteCharAt(sbuilder.length() - 1);
//        String sourceResultIds = sbuilder.toString();
//
//        sbuilder = new StringBuilder();
//        for (Portfolio portfolio : portfolios) {
//            sbuilder.append(portfolio.getId()).append(";");
//        }
//        sbuilder.deleteCharAt(sbuilder.length() - 1);
//        String portfolioIds = sbuilder.toString();
//
//        log.info("userId {}, projectId {}, sourceResultIds {}, portfolioIds {}", userId, projectId, sourceResultIds, portfolioIds);
//
//        try {
//            Job job = jobRegistry.getJob("importLossData");
//            JobParametersBuilder builder = new JobParametersBuilder()
//                    .addString("pdaId", pdaId)
//                    .addString("userId", userId)
//                    .addString("projectId", projectId)
//                    .addString("sourceResultIds", sourceResultIds)
//                    .addString("portfolioIds", portfolioIds);
//            JobExecution execution = null;
//            execution = jobLauncher.run(job, builder.toJobParameters());
//            Long jobExecutionId = execution.getId();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    // new code ri
    @Test
    public void importBatchTest() {
        //        one shot test
//        String pdaId = "PDA_000000705";
//        String userId = "ABRO";
//        String projectId = "P00000019";
//        String sourceResultIds = "P00000022_AV_000316_5109_9";
//        String portfolioIds = "P00000022_AV_000316_5107_6";

//        multishot test
        String rmspicId = "RPIC-000000002";
        String userId = "ABRO";
        String regionPeril = "USWT";
        String fpCode = "GR";

        RmsProjectImportConfig rmsProjectImportConfig = rmsProjectImportConfigRepository.findByProjectImportSourceConfigId(rmspicId);

        Set<Portfolio> portfolios = new HashSet<>();
        Set<SourceResult> sourceResults = new HashSet<>();

        Set<String> uniquePortfolios = new HashSet<>();

        RepresentationDataset representationDataset = representationDatasetRepository.findByRmsProjectImportConfigId(rmsProjectImportConfig.getProjectImportSourceConfigId());

        for (Portfolio port : representationDataset.getRepresentedPortfolios()) {
            if (!uniquePortfolios.contains(port.getPortfolioId())) {
                portfolios.add(port);
            }
            uniquePortfolios.add(port.getPortfolioId());
        }
        sourceResults.add(representationDataset.getRepresentedSourceResults().get(0));




        for (SourceResult sourceResult : sourceResults) {
            sourceResult.setFinalizedRegionPeril(regionPeril);
            sourceResult.setAnalysisFinancialPerspectives(Collections.singletonList(new AnalysisFinancialPerspective(fpCode)));
            sourceResultRepository.save(sourceResult);
        }

        String projectId = rmsProjectImportConfig.getProjectId();

        StringBuilder sbuilder = new StringBuilder();
        for (SourceResult sourceResult : sourceResults) {
            sbuilder.append(sourceResult.getSourceResultId()).append(";");
        }
        sbuilder.deleteCharAt(sbuilder.length() - 1);
        String sourceResultIds = sbuilder.toString();

        sbuilder = new StringBuilder();
        for (Portfolio portfolio : portfolios) {
            sbuilder.append(portfolio.getPortfolioId()).append(";");
        }
        sbuilder.deleteCharAt(sbuilder.length() - 1);
        String portfolioIds = sbuilder.toString();

        log.info("userId {}, projectId {}, sourceResultIds {}, portfolioIds {}", userId, projectId, sourceResultIds, portfolioIds);

        try {
            Job job = jobRegistry.getJob("importLossData");
            JobParametersBuilder builder = new JobParametersBuilder()
                    .addString("rmspicId", rmspicId)
                    .addString("userId", userId)
                    .addString("projectId", projectId)
                    .addString("sourceResultIds", sourceResultIds)
                    .addString("portfolioIds", portfolioIds);
            JobExecution execution = null;
            execution = jobLauncher.run(job, builder.toJobParameters());
            Long jobExecutionId = execution.getId();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    // old code ri
//    @Test
//    public void fullImportTest() {
//        User user = userRepository.findOne("K774");
//
//        Project project = new Project();
//        project.setId("test-project-id");
//        project.setName("test-project-name");
//        project.setAssignedTo(user);
//        project.setClonedFlag(false);
//        project.setCreatedBy("Testet");
//        project.setCreationDate(new Date());
//        project.setRmsInstanceId("RL152");
//        projectRepository.save(project);
//
//        String regionPeril = "NIEQ";
//        String fpCode = "GR";
//        String pdaId = "PDA_000000704";
//        String userId = "MIKN";
//
//        SelectedAssociationBag bag = new SelectedAssociationBag();
//        ProjectDatasourceAssociation projectDatasourceAssociation = new ProjectDatasourceAssociation();
//        projectDatasourceAssociation.setId("test-projectDatasourceAssociation");
//        projectDatasourceAssociation.setAvailableModelingExposureDataSources(null);
//        projectDatasourceAssociation.setAvailableModelingResultDataSources(null);
//        projectDatasourceAssociation.setProject(project);
//        projectDatasourceAssociation.setSelectedAssociationBags(Arrays.asList(bag));
//
//
//
////        try {
////            Job job = jobRegistry.getJob("importLossData");
////            JobParametersBuilder builder = new JobParametersBuilder()
////                    .addString("pdaId", pdaId)
////                    .addString("userId", userId)
////                    .addString("projectId", projectId)
////                    .addString("sourceResultIds", sourceResultIds)
////                    .addString("portfolioIds", portfolioIds);
////            JobExecution execution = null;
////            execution = jobLauncher.run(job, builder.toJobParameters());
////            Long jobExecutionId = execution.getId();
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//
//
//    }

    // new code ri
    @Test
    public void fullImportTest() {
        User user = userRepository.findById(1L).orElse(new User());

        Project project = new Project();
        project.setProjectId("test-project-id");
        project.setName("test-project-name");
        project.setAssignedTo(user);
        project.setClonedFlag(false);
        project.setCreatedBy(user);
        project.setCreationDate(new java.sql.Date((new Date()).getTime()));
        //project.setRmsInstanceId("RL152");
        projectRepository.save(project);

        String regionPeril = "NIEQ";
        String fpCode = "GR";
        String rmspicId = "RPIC_000000704";
        String userId = "MIKN";

        SelectedAssociationBag bag = new SelectedAssociationBag();
        RmsProjectImportConfig rmsProjectImportConfig = new RmsProjectImportConfig();
        rmsProjectImportConfig.setRmsProjectImportConfigId("test-rmsProjectImportConfig");
        rmsProjectImportConfig.setAvailableModelingExposureDataSources(null);
        rmsProjectImportConfig.setAvailableModelingResultDataSources(null);
        rmsProjectImportConfig.setProjectId(project.getProjectId());
        rmsProjectImportConfig.setSelectedAssociationBags(Collections.singletonList(bag));

//        try {
//            Job job = jobRegistry.getJob("importLossData");
//            JobParametersBuilder builder = new JobParametersBuilder()
//                    .addString("pdaId", pdaId)
//                    .addString("userId", userId)
//                    .addString("projectId", projectId)
//                    .addString("sourceResultIds", sourceResultIds)
//                    .addString("portfolioIds", portfolioIds);
//            JobExecution execution = null;
//            execution = jobLauncher.run(job, builder.toJobParameters());
//            Long jobExecutionId = execution.getId();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


    }
}

