package com.scor.rr.service.batch;

import com.scor.rr.domain.dto.RLAnalysisELT;
import com.scor.rr.domain.enums.FinancialPerspectiveCodeEnum;
import com.scor.rr.domain.riskLink.RLAnalysis;
import com.scor.rr.domain.riskLink.RLImportSelection;
import com.scor.rr.domain.riskLink.RLModelDataSource;
import com.scor.rr.repository.RLModelDataSourceRepository;
import com.scor.rr.service.RmsService;
import com.scor.rr.service.state.TransformationBundle;
import com.scor.rr.service.state.TransformationPackage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.keyvalue.MultiKey;
import org.apache.commons.lang.StringUtils;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static java.util.Optional.ofNullable;

@Service
@StepScope
@Slf4j
public class ELTExtractor {


    @Autowired
    RmsService rmsService;

    @Autowired
    TransformationPackage transformationPackage;

    @Autowired
    RLModelDataSourceRepository rlModelDataSourceRepository;


    @Value("#{jobParameters['contractId']}")
    private String contractId;

    @Value("${import.params.threshold}")
    private double threshold;

    public RepeatStatus extractElts() {
        // @TODO: Review with Viet use of Threshold parameter => TO be externalized. & Direct use in corresponding parts
        this.extractELT();
        return RepeatStatus.FINISHED;
    }


    private void extractELT() {

        Map<MultiKey, RLAnalysisELT> extractedELT = new HashMap<>();
        // Given some Bundles

        for (TransformationBundle bundle : transformationPackage.getTransformationBundles()) {
            // start new step (import progress) : step 4 EXTRACT_ELT for this analysis in loop of many analysis

            // Build Parameters
            RLAnalysis riskLinkAnalysis = bundle.getRlAnalysis();
            RLImportSelection sourceResult = bundle.getSourceResult();

            String fpCode = bundle.getFinancialPerspective();
            String defaultInstanceId = bundle.getInstanceId();

            Integer treatyLabelID = isTreaty(fpCode) ? Integer.valueOf(contractId) : null;

            Long analysisId = riskLinkAnalysis.getRlId();
            Long rdmId = riskLinkAnalysis.getRdmId();
            String rdmName = riskLinkAnalysis.getRdmName();

            log.info("Extracting ELT data for rdm {} - {}, analysis {}, fp {} ", rdmId, rdmName, analysisId, fpCode);
            String instanceId = ofNullable(riskLinkAnalysis.getRlModelDataSourceId())
                    .map(dsId -> rlModelDataSourceRepository.findById(dsId).get())
                    .map(RLModelDataSource::getInstanceId)
                    .orElseGet(() -> {
                        log.warn("RmsModelDatasource is null for rlAnalysis {} - use instanceId from batch", riskLinkAnalysis.getRlId());
                        return defaultInstanceId;
                    });


            RLAnalysisELT rlAnalysisELT = extractedELT.get(new MultiKey(instanceId, rdmId, analysisId, fpCode, treatyLabelID));
            if (rlAnalysisELT == null) {
                rlAnalysisELT = rmsService.getAnalysisElt(instanceId, rdmId, rdmName, analysisId, fpCode, treatyLabelID);
                extractedELT.put(new MultiKey(instanceId, rdmId, analysisId, fpCode, treatyLabelID), rlAnalysisELT);
            }
            log.debug("Rms Analysis ELT loss data size = {}", rlAnalysisELT.getEltLosses().size());

            bundle.setRlAnalysisELT(rlAnalysisELT);

            // finish step 4 EXTRACT_ELT for one analysis in loop for of many analysis
            log.info("Finish import progress STEP 4 : EXTRACT_ELT for analysis: {}", sourceResult.getRlImportSelectionId());
        }
    }


    private Boolean isTreaty(String fp) {
        return StringUtils.equals(fp, FinancialPerspectiveCodeEnum.TY.getCode());
    }

}
