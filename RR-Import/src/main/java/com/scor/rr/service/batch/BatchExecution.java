package com.scor.rr.service.batch;

import com.scor.rr.domain.*;
import com.scor.rr.domain.dto.ImportLossDataParams;
import com.scor.rr.domain.dto.ImportParamsDto;
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
    @Qualifier(value = "importLossData")
    private Job importLossData;

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
                        .addString("userId", importLossDataParams.getUserId())
                        .addString("projectId", importLossDataParams.getProjectId())
                        .addString("sourceResultIdsInput", importLossDataParams.getRlImportSelectionIds())
                        .addString("rlPortfolioSelectionIds", importLossDataParams.getRlPortfolioSelectionIds())
                        .addString("instanceId", importLossDataParams.getInstanceId())
                        .addDate("runDate", new Date());

                log.info("Starting import batch: userId {}, projectId {}, sourceResultIds {}", importLossDataParams.getUserId(), importLossDataParams.getProjectId(), importLossDataParams.getRlImportSelectionIds());

                JobExecution execution = jobLauncher.run(importLossData, builder.toJobParameters());
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

        if (workspaceMarketChannel.equals("")) {
            log.error("Error. workspace market channel is not found");
            return null;
        }

        ContractSearchResult contractSearchResult =
                contractSearchResultRepository.findTop1ByWorkSpaceIdAndUwYearOrderByWorkSpaceIdAscUwYearAsc(workspaceCode, myWorkspace.getWorkspaceUwYear()).orElse(null);
        if (contractSearchResult == null) {
            log.error("Error. contract is not found");
            return null;
        }
        String contractId = contractSearchResult.getId();

        ModellingSystemInstanceEntity modellingSystemInstance = modellingSystemInstanceRepository.findById(instanceId).orElse(null);

//        Section section = sectionRepository.findOne(contractId);
//        if (section == null) {
//            log.error("Error. No section found");
//            return null;
//        }
//        Contract contract = contractRepository.findOne(section.getContract().getId());
//        if (contract == null) {
//            log.error("Error. No contract found");
//            return null;
//        }
//        Client client = clientRepository.findOne(contract.getClient().getId());
//        if (client == null) {
//            log.error("Error. No client found");
//            return null;
//        }

        // TODO: Review this
        String carId = "carId" + Math.random();
//        String prefix = myWorkspace.getWorkspaceContextFlag().getValue();
        String reinsuranceType = myWorkspace.getWorkspaceMarketChannel().equals(1L) ? "T" : myWorkspace.getWorkspaceMarketChannel().equals(2L) ? "F" : "";
        String division = "01"; // fixed for TT
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

        String clientName = contractSearchResult.getCedantName();
        String clientId = contractSearchResult.getCedantCode();

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

        return map;
    }
}
