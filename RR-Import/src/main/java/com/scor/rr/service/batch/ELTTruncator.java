package com.scor.rr.service.batch;


import com.scor.rr.domain.RlEltLoss;
import com.scor.rr.domain.dto.RLAnalysisELT;
import com.scor.rr.domain.riskLink.RLAnalysis;
import com.scor.rr.domain.riskLink.RlSourceResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@StepScope
@Slf4j
public class ELTTruncator {

    public RepeatStatus truncateELTs() {
        log.debug("Starting ELTTruncator");
        Date startDate = new Date();

        List<Map> bundles = new ArrayList<>();

        for (Map bundle : bundles) {
            // start new step (import progress) : step 5 TRUNCATE_ELT for this analysis in loop of many analysis :
            RLAnalysis riskLinkAnalysis = (RLAnalysis) bundle.get("RlAnalysis");
            RLAnalysisELT riskLinkAnalysisELT = (RLAnalysisELT) bundle.get("RlAnalysisELT");
            RlSourceResult sourceResult= (RlSourceResult) bundle.get("rlSouceResult");
            log.info("truncating ELT data for analysis " + riskLinkAnalysis.getAnalysisId());
            double threshold = 0;//getThresholdFor(bundle.getRmsAnalysis().getRegion(), bundle.getRmsAnalysis().getPeril(), bundle.getRmsAnalysis().getAnalysisCurrency(), "ELT");
            log.info("Threshold found: " + threshold);
            log.debug("Source ELT size = {}", riskLinkAnalysisELT.getEltLosses().size());

            List<RlEltLoss> truncatedELTLosses = riskLinkAnalysisELT.getEltLosses().stream().filter(i -> i.getLoss() < threshold).collect(toList());

            Double truncatedAAL = truncatedELTLosses.stream().mapToDouble(RlEltLoss::getLoss).sum();
            Double truncatedSD = truncatedELTLosses.stream().mapToDouble(RlEltLoss::getStdDevC).sum();;
            Integer truncatedEvents = truncatedELTLosses.size();

            List<RlEltLoss> validELTLosses = riskLinkAnalysisELT.getEltLosses().stream().filter(i -> i.getLoss() > threshold).collect(toList());
            riskLinkAnalysisELT.setEltLosses(validELTLosses);

            log.debug("Dest ELT size = {}", riskLinkAnalysisELT.getEltLosses().size());
            bundle.put("truncatedAAL", truncatedAAL);
            bundle.put("truncatedEvents",truncatedEvents);
            bundle.put("truncatedSD",truncatedSD);
            bundle.put("truncationCurrency", riskLinkAnalysis.getAnalysisCurrency());
            bundle.put("truncationThreshold",threshold);

            // finish step 5 TRUNCATE_ELT for one analysis in loop for of many analysis
            log.info("Finish import progress STEP 5 : TRUNCATE_ELT for analysis: {}", sourceResult.getRlSourceResultId());
        }
        log.debug("ELTTruncator completed");
        return RepeatStatus.FINISHED;
    }
}
