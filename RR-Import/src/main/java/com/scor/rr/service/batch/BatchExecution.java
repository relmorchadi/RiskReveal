package com.scor.rr.service.batch;

import com.scor.rr.domain.*;
import com.scor.rr.domain.dto.ImportLossDataParams;
import com.scor.rr.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BatchExecution {

    private static final Logger log = LoggerFactory.getLogger(BatchExecution.class);

    @Autowired
    private JobLauncher jobLauncher;

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
    @Qualifier(value = "importLossData")
    private Job importLossData;

    @Autowired
    @Qualifier(value = "importLossDataFac")
    private Job importLossDataFac;

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

                if (params.get("marketChannel").equalsIgnoreCase("Treaty"))
                    execution = jobLauncher.run(importLossData, builder.toJobParameters());
                else
                    execution = jobLauncher.run(importLossDataFac, builder.toJobParameters());

                return execution.getId();
            } else {
                log.error("parameters are empty");
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
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

        if (!myWorkspaceOp.isPresent()) {
            log.error("Error. No workspace found");
            return null;
        }

        WorkspaceEntity myWorkspace = myWorkspaceOp.get();

        String workspaceCode = myWorkspace.getWorkspaceContextCode();
        String workspaceMarketChannel = myWorkspace.getWorkspaceMarketChannel().equals(1L) ? "Treaty" : myWorkspace.getWorkspaceMarketChannel().equals(2L) ? "Fac" : "";

        if (workspaceMarketChannel.equalsIgnoreCase("")) {
            log.error("Error. workspace market channel is not found");
            return null;
        }

        ContractSearchResult contractSearchResult =
                contractSearchResultRepository.findTop1ByWorkSpaceIdAndUwYearOrderByWorkSpaceIdAscUwYearAsc(workspaceCode, myWorkspace.getWorkspaceUwYear()).orElse(null);
        if (contractSearchResult == null && workspaceCode.equalsIgnoreCase("Treaty")) {
            log.error("Error. contract is not found");
            return null;
        }

        ModellingSystemInstanceEntity modellingSystemInstance = modellingSystemInstanceRepository.findById(instanceId).orElse(null);

        ProjectConfigurationForeWriter projectConfigurationForeWriter = null;
        ProjectConfigurationForeWriterContract projectConfigurationForeWriterContract = null;

        if (workspaceMarketChannel.equalsIgnoreCase("Fac")) {
            projectConfigurationForeWriter = projectConfigurationForeWriterRepository.findByProjectId(projectId);
            if (projectConfigurationForeWriter != null) {
                projectConfigurationForeWriterContract = projectConfigurationForeWriterContractRepository
                        .findByProjectConfigurationForeWriterId(projectConfigurationForeWriter.getProjectConfigurationForeWriterId());
            }
        }
        // TODO: Review this
//        String prefix = myWorkspace.getWorkspaceContextFlag().getValue();
        String contractId = projectConfigurationForeWriterContract != null ? projectConfigurationForeWriterContract.getContractId() : contractSearchResult.getId();
        String clientName = projectConfigurationForeWriterContract != null ? projectConfigurationForeWriterContract.getClient() : contractSearchResult.getCedantName();
        String clientId = contractSearchResult != null ? contractSearchResult.getCedantCode() : "1";
        String carId = projectConfigurationForeWriter != null ? projectConfigurationForeWriter.getCaRequestId() : "carId";
        String reinsuranceType = myWorkspace.getWorkspaceMarketChannel().equals(1L) ? "T" : myWorkspace.getWorkspaceMarketChannel().equals(2L) ? "F" : "";
        String lob = projectConfigurationForeWriterContract != null ? projectConfigurationForeWriterContract.getLineOfBusiness() : "";
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
}
