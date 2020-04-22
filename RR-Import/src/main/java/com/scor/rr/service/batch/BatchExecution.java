package com.scor.rr.service.batch;

import com.google.common.base.Joiner;
import com.scor.rr.configuration.security.UserPrincipal;
import com.scor.rr.domain.*;
import com.scor.rr.domain.dto.ImportLossDataParams;
import com.scor.rr.domain.enums.JobPriority;
import com.scor.rr.domain.enums.JobStatus;
import com.scor.rr.repository.*;
import com.scor.rr.service.JobManagerImpl;
import com.scor.rr.service.batch.abstraction.JobManagerAbstraction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BatchExecution {

    private static final Logger log = LoggerFactory.getLogger(BatchExecution.class);

    @Autowired
    @Qualifier(value = "MyJobLauncher")
    private JobLauncher jobLauncher;

    @Autowired
    @Qualifier(value = "jobManagerImpl")
    private JobManagerAbstraction jobManager;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private WorkspaceEntityRepository workspaceRepository;

    @Autowired
    private ContractSearchResultRepository contractSearchResultRepository;

    @Autowired
    private ModellingSystemInstanceRepository modellingSystemInstanceRepository;

    @Autowired
    private ProjectImportRunRepository projectImportRunRepository;

    @Autowired
    private ProjectConfigurationForeWriterRepository projectConfigurationForeWriterRepository;

    @Autowired
    private ProjectConfigurationForeWriterContractRepository projectConfigurationForeWriterContractRepository;

    @Autowired
    private RLImportSelectionRepository rlImportSelectionRepository;

    @Autowired
    private RLPortfolioSelectionRepository rlPortfolioSelectionRepository;

    @Autowired
    private JobEntityRepository jobEntityRepository;

    @Autowired
    @Qualifier(value = "importLossData")
    private Job importLossData;

    @Autowired
    @Qualifier(value = "importLossDataFac")
    private Job importLossDataFac;

    @Autowired
    @Qualifier(value = "importLossDataAnalysis")
    private Job importLossDataAnalysis;

    @Autowired
    @Qualifier(value = "importLossDataPortfolio")
    private Job importLossDataPortfolio;

    @Autowired
    @Qualifier(value = "importLossDataPortfolioFac")
    private Job importLossDataPortfolioFac;

    public Long RunImportLossData(ImportLossDataParams importLossDataParams) {

        try {
            Map<String, String> params = extractNamingProperties(Long.valueOf(importLossDataParams.getProjectId()), importLossDataParams.getInstanceId());

            if (params != null && !params.isEmpty()) {
                JobParametersBuilder builder = new JobParametersBuilder()
                        .addString("reinsuranceType", params.get("reinsuranceType"))
                        .addString("prefix", params.get("prefix"))
                        .addString("clientName", params.get("clientName"))
                        .addString("clientId", params.get("clientId"))
                        .addString("contractId", params.get("contractId"))
                        .addString("division", params.get("division"))
                        .addString("uwYear", params.get("uwYear"))
                        .addString("sourceVendor", params.get("sourceVendor"))
                        .addString("modelSystemVersion", params.get("modelSystemVersion"))
                        .addString("periodBasis", params.get("periodBasis"))
                        .addLong("importSequence", Long.valueOf(params.get("importSequence")))
                        .addString("jobType", params.get("jobType"))
                        .addString("marketChannel", params.get("marketChannel"))
                        .addString("carId", params.get("carId"))
                        .addString("lob", params.get("lob"))
                        .addString("userId", importLossDataParams.getUserId())
                        .addString("projectId", importLossDataParams.getProjectId())
                        .addString("sourceResultIdsInput", importLossDataParams.getRlImportSelectionIds())
                        .addString("rlPortfolioSelectionIds", importLossDataParams.getRlPortfolioSelectionIds())
                        .addString("instanceId", importLossDataParams.getInstanceId())

                        .addDate("runDate", new Date());

                log.info("Starting import batch: userId {}, projectId {}, sourceResultIds {}", importLossDataParams.getUserId(), importLossDataParams.getProjectId(), importLossDataParams.getRlImportSelectionIds());

                JobExecution execution = null;

//                if (params.get("marketChannel").equalsIgnoreCase("Treaty"))
//                    execution = jobLauncher.run(importLossData, builder.toJobParameters());
//                else
//                    execution = jobLauncher.run(importLossDataFac, builder.toJobParameters());

                if (params.get("marketChannel").equalsIgnoreCase("Treaty"))
                    ((JobManagerImpl) jobManager).submitJob(importLossData, JobPriority.MEDIUM, builder.toJobParameters());
                else
                    ((JobManagerImpl) jobManager).submitJob(importLossDataFac, JobPriority.MEDIUM, builder.toJobParameters());

                return 1L;
            } else {
                log.error("parameters are empty");
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public boolean queueImportLossData(String instanceId, Long projectId, Long userId) {

        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            userId = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser().getUserId();
        }

        Map<String, String> params = extractNamingProperties(projectId, instanceId);

        this.createJobsAndTasksAndSubmitThemToQueue(params, projectId, instanceId, userId);

        return true;
    }

    @Transactional(transactionManager = "theTransactionManager")
    public void submitPendingAndRunningTasksToTheQueueAtStartUp() {
        List<JobEntity> jobs = jobEntityRepository.findAllByStatusAndDate();

        for (JobEntity job : jobs) {
            Map<String, String> params = new HashMap<>();

            job.getParams().forEach(e -> {
                if (!e.getParameterName().equalsIgnoreCase("sourceResultIdsInput") && !e.getParameterName().equalsIgnoreCase("rlPortfolioSelectionIds"))
                    params.put(e.getParameterName(), e.getParameterValue());
            });

            for (TaskEntity task : job.getTasks().stream()
                    .filter(t -> t.getStatus().equalsIgnoreCase(JobStatus.PENDING.getCode()) || t.getStatus().equalsIgnoreCase(JobStatus.RUNNING.getCode()))
                    .collect(Collectors.toList())) {

                task.getParams().forEach(e -> params.put(e.getParameterName(), e.getParameterValue()));
                this.submitJobsAndTasksToQueueAfterShutDown(params, task);
            }
        }
    }

    private Map<String, String> extractNamingProperties(Long projectId, String instanceId) {

        Optional<ProjectEntity> projectOp = projectRepository.findById(projectId);

        if (!projectOp.isPresent()) {
            log.error("No project found for projectId={}", projectId);
            return null;
        }

        ProjectEntity project = projectOp.get();
        Optional<WorkspaceEntity> myWorkspaceOp = workspaceRepository.findById(project.getWorkspaceId());
        ProjectConfigurationForeWriter projectConfigurationForeWriter = projectConfigurationForeWriterRepository.findByProjectId(project.getProjectId());

        if (!myWorkspaceOp.isPresent()) {
            log.error("Error. No workspace found");
            return null;
        }

        WorkspaceEntity myWorkspace = myWorkspaceOp.get();

        String workspaceCode = myWorkspace.getWorkspaceContextCode();
        String projectContext = projectConfigurationForeWriter != null ? "Fac" : "Manual";
        String workspaceMarketChannel = projectContext.equals("Manual") ? "Treaty" : "Fac";

//        if (workspaceMarketChannel.equalsIgnoreCase("")) {
//            log.error("Error. workspace market channel is not found");
//            return null;
//        }

        ContractSearchResult contractSearchResult =
                contractSearchResultRepository.findTop1ByWorkSpaceIdAndUwYearOrderByWorkSpaceIdAscUwYearAsc(workspaceCode, myWorkspace.getWorkspaceUwYear()).orElse(null);
        if (contractSearchResult == null && workspaceCode.equalsIgnoreCase("Treaty")) {
            log.error("Error. contract is not found");
            return null;
        }

        ModellingSystemInstanceEntity modellingSystemInstance = modellingSystemInstanceRepository.findById(instanceId).orElse(null);

        //ProjectConfigurationForeWriterContract projectConfigurationForeWriterContract = null;

        //if (workspaceMarketChannel.equalsIgnoreCase("Fac")) {
        //    projectConfigurationForeWriterContract = projectConfigurationForeWriterContractRepository
        //            .findByProjectConfigurationForeWriterId(projectConfigurationForeWriter.getProjectConfigurationForeWriterId());
        //}
        // TODO: Review this
//        String prefix = myWorkspace.getWorkspaceContextFlag().getValue();
        String contractId = myWorkspace.getContractId();
        String clientName = myWorkspace.getClientName();
        String clientId = myWorkspace.getClientId();
        String workspaceName = contractSearchResult != null ? contractSearchResult.getWorkspaceName() : "";
        String carId = projectConfigurationForeWriter != null ? projectConfigurationForeWriter.getCaRequestId() : "carId";
        String reinsuranceType = myWorkspace.getWorkspaceMarketChannel().equals("TTY") ? "T" : myWorkspace.getWorkspaceMarketChannel().equals("FAC") ? "F" : "";
        String lob = myWorkspace.getLob();
        String division = "1"; // fixed for TT
        String periodBasis = "FT"; // fixed for TT
        String sourceVendor = "RMS";
        String prefix = "prefix";
        String jobType = "ACCOUNT";
        String uwYear = String.valueOf(myWorkspace.getWorkspaceUwYear());
        assert modellingSystemInstance != null;
        String modelSystemVersion = modellingSystemInstance.getModellingSystemVersion().getId();

        Long imSeq = null;

        List<ProjectImportRunEntity> lastProjectImportRuns = projectImportRunRepository.findByProjectIdOrderedByStartDate(projectId);

        if (lastProjectImportRuns == null || lastProjectImportRuns.isEmpty()) {
            imSeq = 1L;
        } else {
            imSeq = lastProjectImportRuns.size() + 1L;
        }


        log.info("reinsuranceType {}, prefix {}, clientName {}, clientId {}, contractId {}, division {}, uwYear {}, sourceVendor {}, modelSystemVersion {}, periodBasis {}, importSequence {}",
                reinsuranceType, prefix, clientName, clientId, contractId, division, uwYear, sourceVendor, modelSystemVersion, periodBasis, imSeq);

        Map<String, String> map = new HashMap<>();
        map.put("reinsuranceType", reinsuranceType);
        map.put("prefix", prefix);
        map.put("clientName", clientName);
        map.put("clientId", clientId);
        map.put("workspaceName", workspaceName);
        map.put("contractId", workspaceCode); // use code instead of contract Id
        map.put("division", division);
        map.put("uwYear", uwYear);
        map.put("sourceVendor", sourceVendor);
        map.put("modelSystemVersion", modelSystemVersion);
        map.put("periodBasis", periodBasis);
        map.put("importSequence", String.valueOf(imSeq));
        map.put("projectId", String.valueOf(projectId));
        map.put("instanceId", instanceId);
        map.put("jobType", jobType);
        map.put("marketChannel", workspaceMarketChannel);
        map.put("carId", carId);
        map.put("lob", lob);

        return map;
    }

    private void createJobsAndTasksAndSubmitThemToQueue(Map<String, String> params, Long projectId, String instanceId, Long userId) {

        List<Long> rlImportSelections = rlImportSelectionRepository.findRLImportSelectionIdByProjectId(projectId);
        List<Long> rlPortfolioSelections = rlPortfolioSelectionRepository.findRLPortfolioSelectionIdByProjectId(projectId);

        if ((rlImportSelections != null && !rlImportSelections.isEmpty())
                || (rlPortfolioSelections != null && !rlPortfolioSelections.isEmpty())) {

            if (params != null) {
                params.put("projectId", String.valueOf(projectId));
                params.put("instanceId", instanceId);
                params.put("userId", String.valueOf(userId));

                Map<String, String> jobParams = new HashMap<>(params);
                Joiner joiner = Joiner.on("; ").skipNulls();

                jobParams.put("sourceResultIdsInput", rlImportSelections.stream()
                        .map(Object::toString)
                        .collect(Collectors.joining(";")));

                jobParams.put("rlPortfolioSelectionIds", rlPortfolioSelections.stream()
                        .map(Object::toString)
                        .collect(Collectors.joining(";")));

                JobEntity job = jobManager.createJob(jobParams, JobPriority.MEDIUM.getCode(), userId);

                JobParametersBuilder builder = new JobParametersBuilder();
                builder
                        .addString("reinsuranceType", params.get("reinsuranceType"))
                        .addString("prefix", params.get("prefix"))
                        .addString("clientName", params.get("clientName"))
                        .addString("clientId", params.get("clientId"))
                        .addString("contractId", params.get("contractId"))
                        .addString("division", params.get("division"))
                        .addString("uwYear", params.get("uwYear"))
                        .addString("sourceVendor", params.get("sourceVendor"))
                        .addString("modelSystemVersion", params.get("modelSystemVersion"))
                        .addString("periodBasis", params.get("periodBasis"))
                        .addLong("importSequence", Long.valueOf(params.get("importSequence")))
                        .addString("jobType", params.get("jobType"))
                        .addString("marketChannel", params.get("marketChannel"))
                        .addString("carId", params.get("carId"))
                        .addString("lob", params.get("lob"))
                        .addString("userId", params.get("userId"))
                        .addString("projectId", params.get("projectId"))
                        .addString("instanceId", params.get("instanceId"))
                        .addDate("runDate", new Date());

                for (Long rlImportSelectionId : rlImportSelections) {
                    params.put("sourceResultIdsInput", String.valueOf(rlImportSelectionId));
                    params.put("rlPortfolioSelectionIds", "");

                    TaskEntity task = jobManager.createTask(params, job, JobPriority.MEDIUM.getCode(), TaskType.IMPORT_ANALYSIS);

                    builder
                            .addString("sourceResultIdsInput", params.get("sourceResultIdsInput"))
                            .addString("rlPortfolioSelectionIds", params.get("rlPortfolioSelectionIds"))
                            .addString("taskId", String.valueOf(task.getTaskId()));

                    jobManager.submitJob(importLossDataAnalysis, JobPriority.MEDIUM, builder.toJobParameters());
                }

                for (Long rlPortfolioSelection : rlPortfolioSelections) {
                    params.put("rlPortfolioSelectionIds", String.valueOf(rlPortfolioSelection));
                    params.put("sourceResultIdsInput", "");

                    TaskEntity task = jobManager.createTask(params, job, JobPriority.MEDIUM.getCode(), TaskType.IMPORT_PORTFOLIO);

                    builder
                            .addString("sourceResultIdsInput", params.get("sourceResultIdsInput"))
                            .addString("rlPortfolioSelectionIds", params.get("rlPortfolioSelectionIds"))
                            .addString("taskId", String.valueOf(task.getTaskId()));

                    if (params.get("marketChannel").equalsIgnoreCase("Treaty"))
                        jobManager.submitJob(importLossDataPortfolio, JobPriority.MEDIUM, builder.toJobParameters());
                    else
                        jobManager.submitJob(importLossDataPortfolioFac, JobPriority.MEDIUM, builder.toJobParameters());
                }
            }
        }
    }

    private void submitJobsAndTasksToQueueAfterShutDown(Map<String, String> params, TaskEntity task) {

        if (params != null) {

            JobParametersBuilder builder = new JobParametersBuilder();

            builder
                    .addString("reinsuranceType", params.get("reinsuranceType"))
                    .addString("prefix", params.get("prefix"))
                    .addString("clientName", params.get("clientName"))
                    .addString("clientId", params.get("clientId"))
                    .addString("contractId", params.get("contractId"))
                    .addString("division", params.get("division"))
                    .addString("uwYear", params.get("uwYear"))
                    .addString("sourceVendor", params.get("sourceVendor"))
                    .addString("modelSystemVersion", params.get("modelSystemVersion"))
                    .addString("periodBasis", params.get("periodBasis"))
                    .addLong("importSequence", Long.valueOf(params.get("importSequence")))
                    .addString("jobType", params.get("jobType"))
                    .addString("marketChannel", params.get("marketChannel"))
                    .addString("carId", params.get("carId"))
                    .addString("lob", params.get("lob"))
                    .addString("userId", params.get("userId"))
                    .addString("projectId", params.get("projectId"))
                    .addString("instanceId", params.get("instanceId"))
                    .addString("sourceResultIdsInput", params.get("sourceResultIdsInput"))
                    .addString("rlPortfolioSelectionIds", params.get("rlPortfolioSelectionIds"))
                    .addString("taskId", String.valueOf(task.getTaskId()))
                    .addDate("runDate", new Date());

            if (task.getTaskType().equalsIgnoreCase(TaskType.IMPORT_ANALYSIS.getCode())) {

                jobManager.submitJob(importLossDataAnalysis, JobPriority.HIGH, builder.toJobParameters());

            } else if (task.getTaskType().equalsIgnoreCase(TaskType.IMPORT_PORTFOLIO.getCode())) {

                if (params.get("marketChannel").equalsIgnoreCase("Treaty"))
                    jobManager.submitJob(importLossDataPortfolio, JobPriority.HIGH, builder.toJobParameters());
                else
                    jobManager.submitJob(importLossDataPortfolioFac, JobPriority.HIGH, builder.toJobParameters());
            }
        }
    }
}