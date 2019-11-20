package com.scor.rr.service.batch;


import com.scor.rr.domain.RlEltLoss;
import com.scor.rr.domain.dto.RLAnalysisELT;
import com.scor.rr.domain.riskLink.RLAnalysis;
import com.scor.rr.domain.riskLink.RlSourceResult;
import com.scor.rr.service.state.TransformationBundle;
import com.scor.rr.service.state.TransformationPackage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@Service
@StepScope
@Slf4j
public class ELTTruncator {

    @Value("${import.params.threshold}")
    private double threshold;

    @Autowired
    TransformationPackage transformationPackage;
    public RepeatStatus truncateELTs() {
        log.debug("Starting ELTTruncator");

        List<TransformationBundle> bundles = new ArrayList<>();

        for (TransformationBundle bundle : transformationPackage.getTransformationBundles()) {
            // start new step (import progress) : step 5 TRUNCATE_ELT for this analysis in loop of many analysis :
            RLAnalysis riskLinkAnalysis = bundle.getRlAnalysis();
            RLAnalysisELT riskLinkAnalysisELT = bundle.getRlAnalysisELT();
            RlSourceResult sourceResult=bundle.getSourceResult();
            log.info("truncating ELT data for analysis " + riskLinkAnalysis.getAnalysisId());
            log.info("Threshold found: " + threshold);
            log.debug("Source ELT size = {}", riskLinkAnalysisELT.getEltLosses().size());

            List<RlEltLoss> truncatedELTLosses = riskLinkAnalysisELT.getEltLosses().stream().filter(i -> i.getLoss() < threshold).collect(toList());

            Double truncatedAAL = truncatedELTLosses.stream().mapToDouble(RlEltLoss::getLoss).sum();
            Double truncatedSD = truncatedELTLosses.stream().mapToDouble(RlEltLoss::getStdDevC).sum();;
            Integer truncatedEvents = truncatedELTLosses.size();

            List<RlEltLoss> validELTLosses = riskLinkAnalysisELT.getEltLosses().stream().filter(i -> i.getLoss() > threshold).collect(toList());
            riskLinkAnalysisELT.setEltLosses(validELTLosses);

            log.debug("Dest ELT size = {}", riskLinkAnalysisELT.getEltLosses().size());

            bundle.setTruncatedAAL(truncatedAAL);
            bundle.setTruncatedEvents(truncatedEvents);
            bundle.setTruncatedSD(truncatedSD);
            bundle.setTruncationCurrency(riskLinkAnalysis.getAnalysisCurrency());
            bundle.setTruncationThreshold(threshold);

            // finish step 5 TRUNCATE_ELT for one analysis in loop for of many analysis
            log.info("Finish import progress STEP 5 : TRUNCATE_ELT for analysis: {}", sourceResult.getRlSourceResultId());
        }
        log.debug("ELTTruncator completed");
        return RepeatStatus.FINISHED;
    }
}
