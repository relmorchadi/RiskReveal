package com.scor.rr.service.runnables;

import com.scor.rr.domain.dto.PortfolioHeader;
import com.scor.rr.domain.riskLink.RLPortfolio;
import com.scor.rr.service.RmsService;
import lombok.Data;
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
@Transactional(transactionManager = "rrTransactionManager")
public class PortfolioDetailedScanRunnableTask implements Callable<List<RLPortfolio>> {

    @Autowired
    private RmsService rmsService;

    private CountDownLatch countDownLatch;

    private String instanceId;

    private Long projectId;

    private List<PortfolioHeader> headers;

    @Override
    @Transactional(transactionManager = "rrTransactionManager", propagation = Propagation.REQUIRES_NEW)
    public List<RLPortfolio> call() throws Exception {
        try {
            return rmsService.scanPortfolioDetail(instanceId, headers, projectId);
        } finally {
            countDownLatch.countDown();
        }
    }
}
