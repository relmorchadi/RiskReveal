package com.scor.rr.service.batch;

import com.scor.rr.domain.RmsExchangeRate;
import com.scor.rr.domain.model.LossDataHeader;
import com.scor.rr.domain.riskReveal.RRLossTableHeader;
import com.scor.rr.service.RmsService;
import com.scor.rr.service.state.TransformationBundle;
import com.scor.rr.service.state.TransformationPackage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@StepScope
public class ExchangeRateExtractor {

    private static final Logger log = LoggerFactory.getLogger(ExchangeRateExtractor.class);

    @Autowired
    private TransformationPackage transformationPackage;

    @Autowired
    private RmsService rmsService;


    public void runExchangeRateExtraction() {

        log.debug("Starting RmsExchangeRatesExtractor");

        /**
         * Map<InstanceId, List<CurrencyCode>>
         */
        Map<String, List<String>> ccyInstanceMap = new HashMap<>();
        for (TransformationBundle bundle : transformationPackage.getTransformationBundles()) {

            LossDataHeader sourceRRLT = bundle.getSourceRRLT();
            LossDataHeader conformedRRLT = bundle.getConformedRRLT();

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

            List<RmsExchangeRate> exList = rmsService.getRmsExchangeRates(entry.getKey(), entry.getValue()/*ccyList*/);
            if (exList == null) {
                log.debug("Error in extracting RMS Exchange Rates");
                exList = new ArrayList<>();
            }
            rmsExchangeRates.put(entry.getKey()/*instanceId*/, exList);

        }

        for (TransformationBundle bundle : transformationPackage.getTransformationBundles()) {

            LossDataHeader sourceRRLT = bundle.getSourceRRLT();
            LossDataHeader conformedRRLT = bundle.getConformedRRLT();

            List<RmsExchangeRate> exList = rmsExchangeRates.get(bundle.getInstanceId());
            List<RmsExchangeRate> exchangeRates = new ArrayList<>();

            RmsExchangeRate originalExchangeRate = getRmsExchangeRateForCcy(exList, sourceRRLT.getCurrency());

            if (originalExchangeRate != null) {
                exchangeRates.add(originalExchangeRate);
            } else {
                log.error("Original ELT: exchange rate not found");
            }
            if (!conformedRRLT.getCurrency().equals(sourceRRLT.getCurrency())) {
                RmsExchangeRate targetExchangeRate = getRmsExchangeRateForCcy(exList, conformedRRLT.getCurrency());
                if (targetExchangeRate != null) {
                    exchangeRates.add(targetExchangeRate);
                } else {
                    log.error("Target ELT: exchange rate not found");
                }
            }

            // TODO how to do ?????????????????????????????
            bundle.setRmsExchangeRatesOfRRLT(exchangeRates);

            log.info("Finish import progress STEP 3 : EXTRACT_RMS_EXCHANGE_RATE for analysis: {}", bundle.getSourceResult().getRlSourceResultId());
        }
        log.debug("RmsExchangeRatesExtractor completed");
    }

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
}
