package com.scor.rr.service.runnables;

import com.scor.rr.domain.dto.AnalysisHeader;
import com.scor.rr.domain.riskLink.RLAnalysis;
import com.scor.rr.service.RmsService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

@Component
@Scope("prototype")
@Data
@Slf4j
@Transactional(transactionManager = "rrTransactionManager")
public class AnalysisDetailedScanRunnableTask implements Callable<List<RLAnalysis>> {

    @Autowired
    private RmsService rmsService;

    private CountDownLatch countDownLatch;

    private String instanceId;

    private Long projectId;

    private String selectedFp;

    private List<AnalysisHeader> headers;

    @Override
    @Transactional(transactionManager = "rrTransactionManager", propagation = Propagation.REQUIRES_NEW)
    public List<RLAnalysis> call() throws Exception {
        try {
            return rmsService.scanAnalysisDetail(headers, projectId, selectedFp);
        } finally {
            countDownLatch.countDown();
        }
    }
}
