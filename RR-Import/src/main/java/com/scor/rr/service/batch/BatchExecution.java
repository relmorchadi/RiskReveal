package com.scor.rr.service.batch;

import com.scor.rr.domain.dto.ImportLossDataParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class BatchExecution {

    private static final Logger log = LoggerFactory.getLogger(BatchExecution.class);

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    @Qualifier(value = "importLossData")
    private Job importLossData;

    public Long RunImportLossData(ImportLossDataParams importLossDataParams) {

        try {
            JobParametersBuilder builder = new JobParametersBuilder()
                    .addString("reinsuranceType", null)
                    .addString("prefix", "prefix")
                    .addString("clientName", null)
                    .addString("clientId", "10")
                    .addString("contractId", "01T000031.2014.1.0")
                    .addString("division",null)
                    .addString("uwYear", "2014")
                    .addString("sourceVendor",null)
                    .addString("modelSystemVersion", null)
                    .addString("periodBasis", null)
                    .addLong("importSequence", 1l)
                    .addString("userId", importLossDataParams.getUserId())
                    .addString("projectId", importLossDataParams.getProjectId())
                    .addString("sourceResultIdsInput", importLossDataParams.getRlImportSelectionIds())
                    .addString("rlPortfolioSelectionIds", importLossDataParams.getRlPortfolioSelectionIds())
                    .addString("instanceId", importLossDataParams.getInstanceId())
                    .addDate("runDate", new Date());

            log.info("Starting import batch: userId {}, projectId {}, sourceResultIds {}", importLossDataParams.getUserId(), importLossDataParams.getProjectId(), importLossDataParams.getRlImportSelectionIds());

            JobExecution execution = jobLauncher.run(importLossData, builder.toJobParameters());
            return execution.getId();
        } catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

}
