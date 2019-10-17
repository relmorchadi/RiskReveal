package com.scor.rr.importBatch.processing.elt;

import com.scor.rr.domain.entities.ihub.RRLossTable;
import com.scor.rr.domain.entities.plt.RRAnalysis;
import com.scor.rr.domain.entities.rms.ProjectImportAssetLog;
import com.scor.rr.domain.entities.rms.RMSELTLoss;
import com.scor.rr.domain.entities.rms.RmsAnalysisELT;
import com.scor.rr.domain.entities.rms.RmsExchangeRate;
import com.scor.rr.importBatch.processing.treaty.TransformationBundle;
import com.scor.rr.importBatch.processing.treaty.TransformationPackage;
import com.scor.rr.importBatch.processing.treaty.loss.AnalysisELTnBetaFunction;
import com.scor.rr.importBatch.processing.treaty.loss.ELTLossnBetaFunction;
import com.scor.rr.repository.ihub.RRLossTableRepository;
import com.scor.rr.repository.plt.RRAnalysisRepository;
import com.scor.rr.repository.rms.ProjectImportAssetLogRepository;
import com.scor.rr.utils.Step;
import org.apache.commons.collections.keyvalue.MultiKey;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * Created by u004119 on 01/08/2016.
 */
public class ELTConformer implements ELTHandler {

    private static final Logger log = LoggerFactory.getLogger(ELTTruncator.class);

    @Autowired
    private TransformationPackage transformationPackage;

    @Autowired
    private ProjectImportAssetLogRepository projectImportAssetLogRepository;

    @Autowired
    private RRLossTableRepository rrLossTableRepository;

    @Autowired
    private RRAnalysisRepository rrAnalysisRepository;

    @Override
    public Boolean batchHandle() {
        log.debug("Starting ELTConformer");

        Date startDate = new Date();

        Map<MultiKey, RmsAnalysisELT> conformedELT = new HashMap<>();
        Map<MultiKey, AnalysisELTnBetaFunction> betaConvertFunc = new HashMap<>();

        for (TransformationBundle bundle : transformationPackage.getBundles()) {

            String anlsId = bundle.getRmsAnalysis().getAnalysisId();
            Long rdmId = bundle.getRmsAnalysis().getRdmId();
            String rdmName = bundle.getRmsAnalysis().getRdmName();
            String fpCode = bundle.getFinancialPerspective().getCode(); // not understood ???

            boolean isTY = StringUtils.equalsIgnoreCase(fpCode, "TY");
            Integer treatyLabelID = isTY ? bundle.getFinancialPerspective().getTreatyId() : null;

            String instanceId = null;
            if (bundle.getRmsAnalysis().getRmsModelDatasource() != null && bundle.getRmsAnalysis().getRmsModelDatasource().getInstanceId() != null) {
                instanceId = bundle.getRmsAnalysis().getRmsModelDatasource().getInstanceId();
            }

            // start new step (import progress) : step 6 CONFORM_ELT for this analysis in loop of many analysis :
            ProjectImportAssetLog projectImportAssetLogA = new ProjectImportAssetLog();
            //mongoDBSequence.nextSequenceId(projectImportAssetLogA);
            projectImportAssetLogA.setProjectId(transformationPackage.getProjectId());
            projectImportAssetLogA.setStepId(6);
            projectImportAssetLogA.setStepName(Step.getStepNameFromStepIdForAnalysis(projectImportAssetLogA.getStepId()));
            projectImportAssetLogA.setStartDate(startDate);
            projectImportAssetLogA.setProjectImportLogId(bundle.getProjectImportLogAnalysisId());
            // --------------------------------------------------------------

            // create conformed ELT data and beta function
            RmsAnalysisELT sourceAnalysisPLT = bundle.getRmsAnalysisELT();

            List<RMSELTLoss> conformedELTLosses = new ArrayList<>();

            List<ELTLossnBetaFunction> eltLossesnBetaFunction = new ArrayList<>();

            RmsAnalysisELT conformedAnalysisELT = conformedELT.get(new MultiKey(instanceId, rdmId, anlsId, fpCode, treatyLabelID));
            if (conformedAnalysisELT == null) {
                conformedAnalysisELT = new RmsAnalysisELT(sourceAnalysisPLT.getRdmId(),
                        sourceAnalysisPLT.getRdmName(),
                        sourceAnalysisPLT.getAnalysisId(),
                        sourceAnalysisPLT.getInstanceId(),
                        sourceAnalysisPLT.getFinancialPerspective(),
                        conformedELTLosses);
                conformedELT.put(new MultiKey(instanceId, rdmId, anlsId, fpCode, treatyLabelID), conformedAnalysisELT);
            }

            log.debug("conformedELTLosses.size = {}", conformedELTLosses.size());

            AnalysisELTnBetaFunction analysisELTnBetaFunction = betaConvertFunc.get(new MultiKey(instanceId, rdmId, anlsId, fpCode, treatyLabelID));
            if (analysisELTnBetaFunction == null) {
                analysisELTnBetaFunction = new AnalysisELTnBetaFunction(conformedAnalysisELT.getRdmId(),
                        conformedAnalysisELT.getRdmName(),
                        conformedAnalysisELT.getAnalysisId(),
                        conformedAnalysisELT.getInstanceId(),
                        conformedAnalysisELT.getFinancialPerspective(),
                        eltLossesnBetaFunction);
                betaConvertFunc.put(new MultiKey(instanceId, rdmId, anlsId, fpCode, treatyLabelID), analysisELTnBetaFunction);
            }
            log.debug("eltLossesnBetaFunction.size = {}", eltLossesnBetaFunction.size());

            bundle.setConformedAnalysisELT(conformedAnalysisELT);
            bundle.setAnalysisELTnBetaFunction(analysisELTnBetaFunction);

            //TODO: check null ???
            double proportion = bundle.getSourceResult().getProportion() == null ? 1 : bundle.getSourceResult().getProportion().doubleValue() / 100;
            double multiplier = bundle.getSourceResult().getUnitMultiplier() == null ? 1 : bundle.getSourceResult().getUnitMultiplier().doubleValue();

            log.info("ELT {}, proportion {}, multiplier {}", bundle.getConformedRRLT().getRrLossTableId(), proportion, multiplier);

//            ELTHeader sourceEltHeader = bundle.getSourceELTHeader();
//            ELTHeader conformedEltHeader = bundle.getConformedELTHeader();

            RRLossTable sourceRRLT = bundle.getSourceRRLT();
            RRLossTable conformedRRLT = bundle.getConformedRRLT();

            double exchangeRate = 1.0d;
            double sourceExchangeRate = 1.0d;
            double targetExchangeRate = 1.0d;
            for (RmsExchangeRate rmsExchangeRate : bundle.getRmsExchangeRatesOfRRLT()) {
                if (rmsExchangeRate.getCcy().equals(sourceRRLT.getCurrency()))
                    sourceExchangeRate = rmsExchangeRate.getExchangeRate();
                else if (rmsExchangeRate.getCcy().equals(conformedRRLT.getCurrency()))
                    targetExchangeRate = rmsExchangeRate.getExchangeRate();
                else
                    log.debug("Something wrong: ccy {} found for source RRLT currency {} conformed RRLT currency {}", rmsExchangeRate.getCcy(), sourceRRLT.getCurrency(), conformedRRLT.getCurrency());
            }

            sourceRRLT.setExchangeRate(sourceExchangeRate);
            rrLossTableRepository.save(sourceRRLT);
            conformedRRLT.setExchangeRate(targetExchangeRate);
            rrLossTableRepository.save(conformedRRLT);

            if (!conformedRRLT.getCurrency().equals(sourceRRLT.getCurrency())) {
                exchangeRate = targetExchangeRate / sourceExchangeRate;
                RRAnalysis rrAnalysis = rrAnalysisRepository.findById(conformedRRLT.getRrAnalysisId()).get();
                if (rrAnalysis != null) {
                    rrAnalysis.setExchangeRate(exchangeRate);
                    rrAnalysisRepository.save(rrAnalysis);
                }
            }
            log.debug("source RRLT currency {} conformed RRLT currency {} exchange rate {}", sourceRRLT.getCurrency(), conformedRRLT.getCurrency(), exchangeRate);

            // start conforming
            for (RMSELTLoss rmseltLoss : sourceAnalysisPLT.getEltLosses()) {
                RMSELTLoss conformedELTLoss = new RMSELTLoss(rmseltLoss.getEventId(),
                        rmseltLoss.getRate(),
                        proportion * multiplier * rmseltLoss.getLoss() * exchangeRate,
                        proportion * multiplier * rmseltLoss.getStdDevI() * exchangeRate,
                        proportion * multiplier * rmseltLoss.getStdDevC() * exchangeRate,
                        proportion * multiplier * rmseltLoss.getExposureValue() * exchangeRate);
                conformedELTLosses.add(conformedELTLoss);

                // RMSAnalysisELT to ConformedAnalysisELT
                // ConformedAnalysisELT to AnalysisELTnBetaFunction
                // AnalysisELTnBetaFunction aka ConformedAnalysisELT with build function
                eltLossesnBetaFunction.add(new ELTLossnBetaFunction(conformedELTLoss));
            }

            bundle.updateMinLayerAtt(); // minLayerAtt saved in previous step


            // finish step 6 CONFORM_ELT for one analysis in loop for of many analysis
            Date endDate = new Date();
            projectImportAssetLogA.setEndDate(endDate);
            projectImportAssetLogRepository.save(projectImportAssetLogA);
            log.info("Finish import progress STEP 6 : CONFORM_ELT for analysis: {}", bundle.getSourceResult().getSourceResultId());
        }
        log.debug("ELTConformer completed");
        return true;
    }

}
