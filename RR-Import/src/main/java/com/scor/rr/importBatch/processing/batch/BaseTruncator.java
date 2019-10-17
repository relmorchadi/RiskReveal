package com.scor.rr.importBatch.processing.batch;

import com.scor.rr.domain.entities.references.omega.Currency;
import com.scor.rr.domain.enums.ExchangeRateType;
import com.scor.rr.repository.omega.CurrencyRepository;
import com.scor.rr.repository.references.ExchangeRateRepository;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by U002629 on 10/07/2015.
 */
public class BaseTruncator extends BaseBatchBeanImpl {
    private static final MathContext mc = MathContext.DECIMAL32;
    private ExchangeRateRepository exchangeRateRepository;
    private CurrencyRepository currencyRepository;

    private double thresholdInEur;

    public void setExchangeRateRepository(ExchangeRateRepository exchangeRateRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
    }

    public void setCurrencyRepository(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public double getThresholdFor(String region, String peril, String currency, String lossTableType){

        Map<String, Object> inputParameters = new HashMap<>();
        inputParameters.put("lossTableType", lossTableType);
        inputParameters.put("region", region);
        inputParameters.put("peril", peril);
        inputParameters.put("currency", currency);
        Map<String, Object> thresholds = fireRules(inputParameters, "truncationThreshold");

        BigDecimal threshold = BigDecimal.valueOf((Long) thresholds.get("threshold"));
        String thresholdCCYCode = (String) thresholds.get("thresholdCCY");
        String anchorCCYCode = (String) thresholds.get("anchorCCY");

        if(threshold.intValue()>0) {
            Double rate = 1.0d;
            Double thresholdRate = 1.0d;
            final Currency thresholdCurrency = currencyRepository.findByCode(thresholdCCYCode);
            final Currency currency1 = currencyRepository.findByCode(currency);
            if (currency1 != null) {
                final DateTime start = LocalDate.now().minusYears(1).withDayOfYear(1).toDateTimeAtStartOfDay();
                final Date startDate = start.toDate();
                final DateTime end = start.plusYears(1);
                final Date endDate = end.toDate();
                rate = exchangeRateRepository.findFirstByTypeAndDomesticCurrencyCurrencyIdAndEffectiveDateBetweenOrderByEffectiveDateDesc(ExchangeRateType.YEARLY.getCode(), currency1.getCurrencyId(), startDate, endDate).exchangeRateFor(anchorCCYCode);
                thresholdRate = exchangeRateRepository.findFirstByTypeAndDomesticCurrencyCurrencyIdAndEffectiveDateBetweenOrderByEffectiveDateDesc(ExchangeRateType.YEARLY.getCode(), thresholdCurrency.getCurrencyId(), startDate, endDate).exchangeRateFor(anchorCCYCode);
            }
            BigDecimal rateRP;
            BigDecimal one = BigDecimal.valueOf(1.0d);
            if (rate != null)
                rateRP = BigDecimal.valueOf(rate);
            else
                rateRP = one;

            BigDecimal rateTH;
            if (thresholdRate != null)
                rateTH = BigDecimal.valueOf(thresholdRate);
            else
                rateTH = one;

            this.thresholdInEur = threshold.doubleValue();

            return threshold.multiply(rateTH, mc).multiply(one.divide(rateRP, mc), mc).doubleValue();
        }

        this.thresholdInEur = 0.0d;

        return 0.0d;
    }

    public double getThresholdInEur() {
        return thresholdInEur;
    }

}
