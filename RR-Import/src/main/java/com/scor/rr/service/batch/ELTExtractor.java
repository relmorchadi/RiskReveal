package com.scor.rr.service.batch;

import com.scor.rr.domain.dto.RLAnalysisELT;
import com.scor.rr.domain.enums.FinancialPerspectiveCodeEnum;
import com.scor.rr.domain.riskLink.RLAnalysis;
import com.scor.rr.domain.riskLink.RlSourceResult;
import com.scor.rr.service.RmsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.keyvalue.MultiKey;
import org.apache.commons.lang.StringUtils;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.*;

import static java.util.Optional.ofNullable;

@Service
@StepScope
@Slf4j
public class ELTExtractor {


    @Autowired
    RmsService rmsService;

    public RepeatStatus extractElts() {
        // @TODO: Review with Viet use of Threshold parameter
        this.extractELT(0);
        return RepeatStatus.FINISHED;
    }


    private void extractELT(Integer threshold) {
        Date startDate = new Date();
        Map<MultiKey, RLAnalysisELT> extractedELT = new HashMap<>();
        // Given some Bundles
        List<Map> bundles = new ArrayList<>();

        for (Map bundle : bundles) {
            // start new step (import progress) : step 4 EXTRACT_ELT for this analysis in loop of many analysis

            // Build Parameters
            RLAnalysis riskLinkAnalysis = (RLAnalysis) bundle.get("rlAnalysis");
            RlSourceResult sourceResult = (RlSourceResult) bundle.get("sourceResult");
            String fpCode = (String) bundle.get("fpPerspectiveCode"); // not understood ???
            // @TODO: Review where we can get the Instance Batch Param
            Integer defaultInstanceId = (Integer) bundle.get("defaultInstanceId"); // not understood ???
            Integer treatyLabelID = isTreaty(fpCode) ? (Integer) bundle.get("treatyLabelId") : null;
            BigInteger analysisId = riskLinkAnalysis.getAnalysisId();
            BigInteger rdmId = riskLinkAnalysis.getRdmId();
            String rdmName = riskLinkAnalysis.getRdmName();

            log.info("Extracting ELT data for rdm {} - {}, analysis {}, fp {} ", rdmId, rdmName, analysisId, fpCode);
            Integer instanceId = ofNullable(riskLinkAnalysis.getRlModelDataSourceId()).orElseGet(() -> {
                log.warn("RmsModelDatasource is null for rmsAnalysis {} - use instanceId from batch", riskLinkAnalysis.getAnalysisId());
                return defaultInstanceId;
            });


            RLAnalysisELT rlAnalysisELT = extractedELT.get(new MultiKey(instanceId, rdmId, analysisId, fpCode, treatyLabelID));
            if (rlAnalysisELT == null) {
                rlAnalysisELT = rmsService.getAnalysisElt(instanceId, rdmId.longValue(), rdmName, analysisId.longValue(), fpCode, treatyLabelID);
                extractedELT.put(new MultiKey(instanceId, rdmId, analysisId, fpCode, treatyLabelID), rlAnalysisELT);
            }
            log.debug("Rms Analysis ELT loss data size = {}", rlAnalysisELT.getEltLosses().size());

            // @TODO: Review the bundle output
            bundle.put("rlAnalysisELT", rlAnalysisELT);
            bundle.put("MinLayerAtt", Double.valueOf(threshold));

            // finish step 4 EXTRACT_ELT for one analysis in loop for of many analysis
            log.info("Finish import progress STEP 4 : EXTRACT_ELT for analysis: {}", sourceResult.getRlSourceResultId());
        }
    }


    private Boolean isTreaty(String fp) {
        return StringUtils.equals(fp, FinancialPerspectiveCodeEnum.TY.getCode());
    }

}
