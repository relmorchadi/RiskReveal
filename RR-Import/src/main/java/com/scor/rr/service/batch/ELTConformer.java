package com.scor.rr.service.batch;


import com.scor.rr.domain.RlEltLoss;
import com.scor.rr.domain.RmsExchangeRate;
import com.scor.rr.domain.dto.AnalysisELTnBetaFunction;
import com.scor.rr.domain.dto.ELTLossnBetaFunction;
import com.scor.rr.domain.dto.RLAnalysisELT;
import com.scor.rr.domain.enums.FinancialPerspectiveCodeEnum;
import com.scor.rr.domain.model.LossDataHeader;
import com.scor.rr.domain.riskLink.RLAnalysis;
import com.scor.rr.domain.riskLink.RlSourceResult;
import com.scor.rr.repository.RRAnalysisRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.keyvalue.MultiKey;
import org.apache.commons.lang.StringUtils;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.util.Optional.ofNullable;

@Service
@StepScope
@Slf4j
public class ELTConformer {

    @Autowired
    RRAnalysisRepository rrAnalysisRepository;

    public RepeatStatus conformeELT(){
        log.debug("Starting ELTConformer");

        Date startDate = new Date();

        Map<MultiKey, RLAnalysisELT> conformedELT = new HashMap<>();
        Map<MultiKey, AnalysisELTnBetaFunction> betaConvertFunc = new HashMap<>();
        List<Map> bundles = new ArrayList<>();

        for (Map bundle : bundles) {
            // Extract Params
            RLAnalysis rlAnalysis = (RLAnalysis) bundle.get("rlAnalysis");
            Long anlsId = rlAnalysis.getAnalysisId().longValue();
            Long rdmId =rlAnalysis.getRdmId().longValue();
            String rdmName = rlAnalysis.getRdmName();
            String fpCode = (String) bundle.get("fpCode"); // not understood ???
            Integer treatyLabelID = isTreaty(fpCode) ? (Integer) bundle.get("treatyLabelId") : null;
            Integer instanceId = rlAnalysis.getRlModelDataSourceId();
            RLAnalysisELT sourceAnalysisPLT =  (RLAnalysisELT) bundle.get("RlAnalysisELT");
            RlSourceResult sourceResult= (RlSourceResult) bundle.get("rlSourceResult");

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

            bundle.put("conformedAnalysisELT",conformedAnalysisELT);
            bundle.put("AnalysisELTnBetaFunction", analysisELTnBetaFunction);

            //TODO: check null ???
            double proportion = ofNullable(sourceResult.getProportion()).map(Number::doubleValue).map( val -> val / 100).orElse(1d);
            double multiplier = ofNullable(sourceResult.getUnitMultiplier()).map(Number::doubleValue).orElse(1d);

            log.info("ELT {}, proportion {}, multiplier {}", conformedAnalysisELT.getAnalysisId(), proportion, multiplier);

            LossDataHeader sourceRRLT = (LossDataHeader) bundle.get("SourceRRLT");
            LossDataHeader conformedRRLT = (LossDataHeader) bundle.get("ConformedRRLT");
            List<RmsExchangeRate> rlExchangeRates= (List) bundle.get("RmsExchangeRatesOfRRLT");

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

            /** Eliminate Redundant ExchangeRate Between LossDataHeader / RRAnalysis(ModelAnalysis) Table
            sourceRRLT.setExchangeRate(sourceExchangeRate);
            rrLossTableRepository.save(sourceRRLT);
            conformedRRLT.setExchangeRate(targetExchangeRate);
            rrLossTableRepository.save(conformedRRLT);
            */

            if (! conformedRRLT.getCurrency().equals(sourceRRLT.getCurrency())) {
                exchangeRate = targetExchangeRate / sourceExchangeRate;
                rrAnalysisRepository.updateExchangeRateByAnalysisId(conformedRRLT.getModelAnalysisId(), exchangeRate);
            }

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

            /** @TODO Review the update Min Layer Attribute Logic **/
            Double minLayerAtt= (Double) bundle.get("minLayerAtt");
            for (ELTLossnBetaFunction ELTLossnBetaFunction : eltLossesnBetaFunction) {
                ELTLossnBetaFunction.setMinLayerAtt(minLayerAtt);
            }

            bundle.put("eltLossesnBetaFunction", eltLossesnBetaFunction);

            log.info("Finish import progress STEP 6 : CONFORM_ELT for analysis: {}", sourceResult.getRlSourceResultId());
        }
        log.debug("ELTConformer completed");
        return RepeatStatus.FINISHED;
    }


    private Boolean isTreaty(String fp) {
        return StringUtils.equals(fp, FinancialPerspectiveCodeEnum.TY.getCode());
    }
}
