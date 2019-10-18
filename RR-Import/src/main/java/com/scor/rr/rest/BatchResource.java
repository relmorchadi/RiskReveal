package com.scor.rr.rest;

import com.scor.rr.domain.entities.omega.Contract;
import com.scor.rr.domain.entities.omega.Section;
import com.scor.rr.domain.entities.plt.ProjectImportRun;
import com.scor.rr.domain.entities.references.cat.ModellingSystemInstance;
import com.scor.rr.domain.entities.references.omega.Client;
import com.scor.rr.domain.entities.rms.RmsProjectImportConfig;
import com.scor.rr.domain.entities.workspace.Project;
import com.scor.rr.domain.entities.workspace.Workspace;
import com.scor.rr.importBatch.processing.batch.RequestCache;
import com.scor.rr.repository.cat.ModellingSystemInstanceRepository;
import com.scor.rr.repository.omega.ClientRepository;
import com.scor.rr.repository.omega.ContractRepository;
import com.scor.rr.repository.omega.SectionRepository;
import com.scor.rr.repository.plt.ProjectImportRunRepository;
import com.scor.rr.repository.rms.RmsProjectImportConfigRepository;
import com.scor.rr.repository.workspace.ProjectRepository;
import com.scor.rr.repository.workspace.WorkspaceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.admin.service.JobService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class BatchResource {

    private final Logger log = LoggerFactory.getLogger(BatchResource.class);

    @Autowired
    @Qualifier(value = "myJobLauncher")
    JobLauncher jobLauncher;

    @Autowired
    @Qualifier(value = "myJobRegistry")
    JobRegistry jobRegistry;

    @Autowired
    @Qualifier(value = "myJobService")
    private JobService jobService;

    @Qualifier("requestCache")
    @Autowired
    private RequestCache cache;

    @Autowired
    ProjectImportRunRepository projectImportRunRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    WorkspaceRepository workspaceRepository;

    @Autowired
    ContractRepository contractRepository;

    @Autowired
    SectionRepository sectionRepository;

    @Autowired
    RmsProjectImportConfigRepository rmsProjectImportConfigRepository;

    @Autowired
    ModellingSystemInstanceRepository modellingSystemInstanceRepository;

    @Autowired
    ClientRepository clientRepository;

    @GetMapping("trigger")
    public ResponseEntity runBatchJob(
            @RequestParam("batchJobName") String batchJobName,
            @RequestParam("catReqId") String catReqId,
            @RequestParam("edm") String edm,
            @RequestParam("edmId") long edmId,
            @RequestParam("rdm") String rdm,
            @RequestParam("rdmId") long rdmId,
            @RequestParam("portfolio") String portfolio,
            @RequestParam("division") String division,
            @RequestParam("periodBasis") String periodBasis,
            @RequestParam("version") String version,
            @RequestParam("fp1") String fp1,
            @RequestParam("fp2") String fp2,
            @RequestParam("instanceId") String instanceId
    ) throws JobExecutionException {
        Job job = jobRegistry.getJob(batchJobName);
        JobParametersBuilder builder = new JobParametersBuilder(job.getJobParametersIncrementer().getNext(jobService.getLastJobParameters(batchJobName)))
                .addString("catReqId", catReqId)
                .addString("edm", edm)
                .addLong("edmId", edmId)
                .addString("rdm", rdm)
                .addLong("rdmId", rdmId)
                .addString("portfolio", portfolio)
                .addString("division", division)
                .addString("periodBasis", periodBasis)
                .addLong("version", version == null ? 0l : Long.parseLong(version))
                .addString("fpELT", fp1)
                .addString("fpStats", fp2)
                .addString("instanceId", instanceId)
                .addString("correlationId", UUID.randomUUID().toString())
                .addDate("runDate", new Date());
        JobExecution execution = jobLauncher.run(job, builder.toJobParameters());
        Long jobExecutionId = execution.getId();
        cache.queueRequest(catReqId, division, periodBasis);
        return new ResponseEntity(jobExecutionId, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity runImportBatchJob(
            @RequestParam("batchJobName") String batchJobName,
            @RequestParam("instanceId") String instanceId,
            @RequestParam("rmspicId") String rmspicId,
            @RequestParam("userId") String userId,
            @RequestParam("projectId") String projectId,
            @RequestParam("sourceResultIds") String sourceResultIds,
            @RequestParam("portfolioIds") String portfolioIds
    ) throws JobExecutionException {
        log.info("Starting runImportBatchJob at backend");

        Map<String, Object> properties = extractNamingProperties(projectId, rmspicId, instanceId);
        if (properties == null) {
            return new ResponseEntity("Error. No workspace found", HttpStatus.OK);
        }
        Job job = jobRegistry.getJob(batchJobName);
        JobParametersBuilder builder = new JobParametersBuilder(job.getJobParametersIncrementer().getNext(jobService.getLastJobParameters(batchJobName)))
                .addString("instanceId", instanceId)
                .addString("reinsuranceType", (String) properties.get("reinsuranceType"))
                .addString("prefix", (String) properties.get("prefix"))
                .addString("clientName", (String) properties.get("clientName"))
                .addString("clientId", (String) properties.get("clientId"))
                .addString("contractId", (String) properties.get("contractId"))
                .addString("division", (String) properties.get("division"))
                .addString("uwYear", (String) properties.get("uwYear"))
                .addString("sourceVendor", (String) properties.get("sourceVendor"))
                .addString("modelSystemVersion", (String) properties.get("modelSystemVersion"))
                .addString("periodBasis", (String) properties.get("periodBasis"))
                .addLong("importSequence", (Long) properties.get("importSequence"))
                .addString("catReqId", rmspicId)
                .addString("rmspicId", rmspicId)
                .addString("userId", userId)
                .addString("projectId", projectId)
                .addString("sourceResultIds", sourceResultIds)
                .addString("portfolioIds", portfolioIds)
                .addDate("runDate", new Date());
        log.info("Starting import batch: rmspicId {}, userId {}, projectId {}, sourceResultIds {}, portfolioIds {}", rmspicId, userId, projectId, sourceResultIds, portfolioIds);
        //FIXME: thread leak here
        JobExecution execution = jobLauncher.run(job, builder.toJobParameters());
        Long jobExecutionId = execution.getId();
        return new ResponseEntity(jobExecutionId, HttpStatus.OK);
    }

    private Map<String, Object> extractNamingProperties(String projectId, String rmspicId, String instanceId) {
        Project project = projectRepository.findById(projectId).orElse(null);
        if (project == null) {
            log.error("No project found for projectId={}", projectId);
            return null;
        }

        Workspace myWorkspace = workspaceRepository.findByProjectId(projectId);

        if (myWorkspace == null) {
            log.error("Error. No workspace found");
            return null;
        }

        String workspaceCode = myWorkspace.getWorkspaceContext().getWorkspaceContextCode();
        String contractId = myWorkspace.getContractId();
        if ((contractId == null) || contractId.isEmpty()) {
            log.error("Error. contractId is null or empty");
            return null;
        }

        RmsProjectImportConfig rmspic = rmsProjectImportConfigRepository.findByProjectImportSourceConfigId(rmspicId);
        ModellingSystemInstance modellingSystemInstance = modellingSystemInstanceRepository.findById(instanceId).orElse(null);
        Section section = sectionRepository.findByContractId(contractId);
        if (section == null) {
            log.error("Error. No section found");
            return null;
        }
        Contract contract = contractRepository.findById(section.getContract().getContractId()).orElse(null);
        if (contract == null) {
            log.error("Error. No contract found");
            return null;
        }
        Client client = clientRepository.findById(contract.getClient().getClientId()).orElse(null);
        if (client == null) {
            log.error("Error. No client found");
            return null;
        }

        String reinsuranceType = "T"; // fixed for TT
        String division = "01"; // fixed for TT
        String sourceVendor = "RMS";
        String periodBasis = "FT"; // fixed for TT
        String prefix = myWorkspace.getWorkspaceContext().getWorkspaceContextFlag().getCode();
        String uwYear = myWorkspace.getWorkspaceContext().getWorkspaceUwYear() + "-01";
        String modelSystemVersion = modellingSystemInstance.getModellingSystemVersion().getId();

        Long imSeq = null;
        if (rmspic == null) {
            log.error("No RmsProjectImportConfig for rmspicId = {}", rmspicId);
            return null;
        }

        ProjectImportRun lastProjectImportRun = null;
        if (rmspic.getLastProjectImportRunId() != null) {
            lastProjectImportRun = projectImportRunRepository.findById(rmspic.getLastProjectImportRunId()).orElse(null);
        }

        List<ProjectImportRun> projectImportRuns = projectImportRunRepository.findByProjectProjectId(projectId);
        if (projectImportRuns == null) {
            imSeq = 1L;
        } else {
            imSeq = projectImportRuns.size() + 1L;
        }

        log.info("BatchRest: rmspicId {}, lastProjectImportRunId {}, runId {}", rmspic.getRmsProjectImportConfigId(), lastProjectImportRun == null ? null : lastProjectImportRun.getProjectImportRunId(), lastProjectImportRun == null ? null : lastProjectImportRun.getRunId());



        String clientName = client.getClientShortName();
        String clientId = client.getClientId();

        log.info("reinsuranceType {}, prefix {}, clientName {}, clientId {}, contractId {}, division {}, uwYear {}, sourceVendor {}, modelSystemVersion {}, periodBasis {}, importSequence {}",
                reinsuranceType, prefix, clientName, clientId, contractId, division, uwYear, sourceVendor, modelSystemVersion, periodBasis, imSeq);

        Map<String, Object> map = new HashMap<>();
        map.put("reinsuranceType", reinsuranceType);
        map.put("prefix", prefix);
        map.put("clientName", clientName);
        map.put("clientId", clientId);
        map.put("contractId", workspaceCode); // use code instead of contract Id
        map.put("division", division);
        map.put("uwYear", uwYear);
        map.put("sourceVendor", sourceVendor);
        map.put("modelSystemVersion", modelSystemVersion);
        map.put("periodBasis", periodBasis);
        map.put("importSequence", imSeq);
        map.put("projectId", projectId);
        map.put("rmspicId", rmspicId);
        map.put("instanceId", instanceId);

        return map;
    }
}
