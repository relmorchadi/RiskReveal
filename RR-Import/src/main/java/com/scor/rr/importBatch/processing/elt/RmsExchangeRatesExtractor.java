package com.scor.rr.importBatch.processing.elt;

import com.scor.rr.domain.entities.ihub.RRLossTable;
import com.scor.rr.domain.entities.rms.ProjectImportAssetLog;
import com.scor.rr.domain.entities.rms.RmsExchangeRate;
import com.scor.rr.importBatch.processing.batch.rms.BaseRMSBeanImpl;
import com.scor.rr.importBatch.processing.treaty.TransformationBundle;
import com.scor.rr.importBatch.processing.treaty.TransformationPackage;
import com.scor.rr.repository.ihub.RRLossTableRepository;
import com.scor.rr.repository.rms.ProjectImportAssetLogRepository;
import com.scor.rr.repository.tracking.ProjectImportLogRepository;
import com.scor.rr.utils.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;


public class RmsExchangeRatesExtractor extends BaseRMSBeanImpl {
    @Autowired
    private TransformationPackage transformationPackage;

    // old code ri
//    @Autowired
//    private ELTHeaderRepository eltHeaderRepository;

    // new code ri
    @Autowired
    private RRLossTableRepository rrLossTableRepository;

    @Autowired
    private ProjectImportLogRepository projectImportLogRepository;

    @Autowired
    private ProjectImportAssetLogRepository projectImportAssetLogRepository;

    @Autowired
    private RRLossTableRepository rrImportedLossDataRepository;

    private static final Logger log= LoggerFactory.getLogger(RmsExchangeRatesExtractor.class);

    private RmsExchangeRate getRmsExchangeRateForCcy(List<RmsExchangeRate> rmsExchangeRates, String ccy) {
        if (rmsExchangeRates != null && !rmsExchangeRates.isEmpty()) {
            for (RmsExchangeRate exchangeRate : rmsExchangeRates) {
                if (exchangeRate.getCcy().equals(ccy))
                    return exchangeRate;
            }
        }
        log.error("No exchange rate found - something is really wrong !!!");
        return null;
    }

    public void runExtraction() {
        log.debug("Starting RmsExchangeRatesExtractor");

        Date startDate = new Date();

        Map<String, List<String>> ccyInstanceMap = new HashMap<>();
        for (TransformationBundle bundle : transformationPackage.getBundles()) {
//            ELTHeader sourceELTHeader = bundle.getSourceELTHeader();
//            ELTHeader conformedELTHeader = bundle.getConformedELTHeader();

            RRLossTable sourceRRLT = bundle.getSourceRRLT();
            RRLossTable conformedRRLT = bundle.getConformedRRLT();

            List<String> ccyList = ccyInstanceMap.get(bundle.getInstanceId());
            if (ccyList == null) {
                ccyList = new ArrayList<>();
            }
            if (!ccyList.contains(sourceRRLT.getCurrency()))
                ccyList.add(sourceRRLT.getCurrency());
            if (!ccyList.contains(conformedRRLT.getCurrency()))
                ccyList.add(conformedRRLT.getCurrency());
            ccyInstanceMap.put(bundle.getInstanceId(), ccyList);
        }

        Map<String, List<RmsExchangeRate>> rmsExchangeRates = new HashMap<>(ccyInstanceMap.size());
        for (Map.Entry<String, List<String>> entry : ccyInstanceMap.entrySet()) {
            List<RmsExchangeRate> exList = rmsDataProvider.extractRMSExchangeRates(entry.getKey()/*instanceId*/, entry.getValue()/*ccyList*/);
            if (exList == null) {
                log.debug("Error in extracting RMS Exchange Rates");
                exList = new ArrayList<>();
            }
            rmsExchangeRates.put(entry.getKey()/*instanceId*/, exList);
        }

        for (TransformationBundle bundle : transformationPackage.getBundles()) {
            // start new step (import progress) : step 3 EXTRACT_RMS_EXCHANGE_RATE for this analysis in loop of many analysis :
            ProjectImportAssetLog projectImportAssetLogA = new ProjectImportAssetLog();
            projectImportAssetLogA.setProjectId(getProjectId());
            projectImportAssetLogA.setStepId(3);
            projectImportAssetLogA.setStepName(Step.getStepNameFromStepIdForAnalysis(projectImportAssetLogA.getStepId()));
            projectImportAssetLogA.setStartDate(startDate);
            projectImportAssetLogA.setProjectImportLogId(bundle.getProjectImportLogAnalysisId());
            // --------------------------------------------------------------

//            ELTHeader sourceELTHeader = bundle.getSourceELTHeader();
//            ELTHeader conformedELTHeader = bundle.getConformedELTHeader();

            RRLossTable sourceRRLT = bundle.getSourceRRLT();
            RRLossTable conformedRRLT = bundle.getConformedRRLT();

            List<RmsExchangeRate> exList = rmsExchangeRates.get(bundle.getInstanceId());
            List<RmsExchangeRate> exchangeRates = new ArrayList<>();
            RmsExchangeRate originalExchangeRate = getRmsExchangeRateForCcy(exList, sourceRRLT.getCurrency());
            if (originalExchangeRate != null) {
                exchangeRates.add(originalExchangeRate);
            } else {
                log.error("Original ELT: exchange rate not found");
                projectImportAssetLogA.getErrorMessages().add("Original ELT: exchange rate not found" + bundle.getSourceResult().getSourceResultId());
            }
            if (!conformedRRLT.getCurrency().equals(sourceRRLT.getCurrency())) {
                RmsExchangeRate targetExchangeRate = getRmsExchangeRateForCcy(exList, conformedRRLT.getCurrency());
                if (targetExchangeRate != null) {
                    exchangeRates.add(targetExchangeRate);
                } else {
                    log.error("Target ELT: exchange rate not found");
                    projectImportAssetLogA.getErrorMessages().add("Target ELT: exchange rate not found" + bundle.getSourceResult().getSourceResultId());
                }
            }

            // TODO how to do ?????????????????????????????
            bundle.setRmsExchangeRatesOfRRLT(exchangeRates);

            // old code
//            sourceELTHeader.setRmsExchangeRates(exchangeRates);
//            conformedELTHeader.setRmsExchangeRates(exchangeRates);

//            rrImportedLossDataRepository.save(sourceELTHeader);
//            rrImportedLossDataRepository.save(conformedELTHeader);

            // finis step 3 EXTRACT_RMS_EXCHANGE_RATE for one analysis in loop for of many analysis
            Date endDate = new Date();
            projectImportAssetLogA.setEndDate(endDate);
            projectImportAssetLogRepository.save(projectImportAssetLogA);
            log.info("Finish import progress STEP 3 : EXTRACT_RMS_EXCHANGE_RATE for analysis: {}", bundle.getSourceResult().getSourceResultId());
        }
        log.debug("RmsExchangeRatesExtractor completed");
    }
}
