package com.scor.rr.service.batch;


import com.scor.rr.domain.RlEltLoss;
import com.scor.rr.domain.RmsExchangeRate;
import com.scor.rr.domain.dto.AnalysisELTnBetaFunction;
import com.scor.rr.domain.dto.ELTLossnBetaFunction;
import com.scor.rr.domain.dto.RLAnalysisELT;
import com.scor.rr.domain.enums.FinancialPerspectiveCodeEnum;
import com.scor.rr.domain.LossDataHeaderEntity;
import com.scor.rr.domain.riskLink.RLAnalysis;
import com.scor.rr.domain.riskLink.RLImportSelection;
import com.scor.rr.repository.ModelAnalysisEntityRepository;
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

import java.util.*;

import static java.util.Optional.ofNullable;

@Service
@StepScope
@Slf4j
public class ELTConformer {

    @Autowired
    ModelAnalysisEntityRepository rrAnalysisRepository;

    @Autowired
    TransformationPackage transformationPackage;

    @Value("#{jobParameters['contractId']}")
    String contractId;

    public RepeatStatus conformeELT(){
        log.debug("Starting ELTConformer");

        Date startDate = new Date();

        Map<MultiKey, RLAnalysisELT> conformedELT = new HashMap<>();
        Map<MultiKey, AnalysisELTnBetaFunction> betaConvertFunc = new HashMap<>();

        for (TransformationBundle bundle : transformationPackage.getTransformationBundles()) {
            // Extract Params
            RLAnalysis rlAnalysis = bundle.getRlAnalysis();
            Long anlsId = rlAnalysis.getAnalysisId();
            Long rdmId =rlAnalysis.getRdmId();
            String rdmName = rlAnalysis.getRdmName();
            String fpCode = bundle.getFinancialPerspective(); // not understood ???
            Integer treatyLabelID = isTreaty(fpCode) ? Integer.valueOf(contractId) : null;
            String instanceId = bundle.getInstanceId();
            RLAnalysisELT sourceAnalysisPLT =  bundle.getRlAnalysisELT();
            RLImportSelection sourceResult= bundle.getSourceResult();

            List<RlEltLoss> conformedELTLosses = new ArrayList<>();

            List<ELTLossnBetaFunction> eltLossesnBetaFunction = new ArrayList<>();

            RLAnalysisELT conformedAnalysisELT = conformedELT.get(new MultiKey(instanceId, rdmId, anlsId, fpCode, treatyLabelID));
            if (conformedAnalysisELT == null) {
                conformedAnalysisELT = new RLAnalysisELT(
                        sourceAnalysisPLT.getInstanceId(),
                        sourceAnalysisPLT.getRdmId(),
                        sourceAnalysisPLT.getRdmName(),
                        sourceAnalysisPLT.getAnalysisId(),
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

            bundle.setConformedRlAnalysisELT(conformedAnalysisELT);
            bundle.setAnalysisELTnBetaFunction(analysisELTnBetaFunction);

            //TODO: check null ???
            double proportion = ofNullable(sourceResult.getProportion()).map(Number::doubleValue).map( val -> val / 100).orElse(1d);
            double multiplier = ofNullable(sourceResult.getUnitMultiplier()).map(Number::doubleValue).orElse(1d);

            log.info("ELT {}, proportion {}, multiplier {}", conformedAnalysisELT.getAnalysisId(), proportion, multiplier);

            LossDataHeaderEntity sourceRRLT = bundle.getSourceRRLT();
            LossDataHeaderEntity conformedRRLT = bundle.getConformedRRLT();
            List<RmsExchangeRate> rlExchangeRates= bundle.getRmsExchangeRatesOfRRLT();

            double exchangeRate = 1.0d;
            double sourceExchangeRate = 1.0d;
            double targetExchangeRate = 1.0d;

            for (RmsExchangeRate rmsExchangeRate : rlExchangeRates) {
                if (rmsExchangeRate.getCcy().equals(sourceRRLT.getCurrency()))
                    sourceExchangeRate = rmsExchangeRate.getExchangeRate();
                else if (rmsExchangeRate.getCcy().equals(conformedRRLT.getCurrency()))
                    targetExchangeRate = rmsExchangeRate.getExchangeRate();
                else
                    log.debug("Something wrong: ccy {} found for source RRLT currency {} conformed RRLT currency {}", rmsExchangeRate.getCcy(), sourceRRLT.getCurrency(),conformedRRLT.getCurrency());
            }

            if (! conformedRRLT.getCurrency().equals(sourceRRLT.getCurrency())) {
                exchangeRate = targetExchangeRate / sourceExchangeRate;
            }
            rrAnalysisRepository.updateExchangeRateByAnalysisId(conformedRRLT.getModelAnalysisId(), exchangeRate);

            log.debug("source RRLT currency {} conformed RRLT currency {} exchange rate {}", sourceRRLT.getCurrency(),conformedRRLT.getCurrency(), exchangeRate) ;

            // start conforming
            for (RlEltLoss rmseltLoss : sourceAnalysisPLT.getEltLosses()) {
                RlEltLoss conformedELTLoss = new RlEltLoss(rmseltLoss.getEventId(),
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

            /** @TODO Review the update Min Layer Attribute Logic => Delete + Threshold default Val on ELTLossnBetaFunction **/
            for (ELTLossnBetaFunction ELTLossnBetaFunction : eltLossesnBetaFunction) {
                ELTLossnBetaFunction.setMinLayerAtt(0d);
            }

            //@TODO Review
            //bundle.setAnalysisELTnBetaFunction(eltLossesnBetaFunction);

            log.info("Finish import progress STEP 6 : CONFORM_ELT for analysis: {}", sourceResult.getRlSourceResultId());
        }
        log.debug("ELTConformer completed");
        return RepeatStatus.FINISHED;
    }


    private Boolean isTreaty(String fp) {
        return StringUtils.equals(fp, FinancialPerspectiveCodeEnum.TY.getCode());
    }
}
