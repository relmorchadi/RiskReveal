package com.scor.rr.service.adjustement.pltAdjustment;

import com.scor.rr.domain.dto.AEPMetric;
import com.scor.rr.domain.dto.OEPMetric;
import com.scor.rr.domain.dto.adjustement.loss.PLTLossData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatisticAdjustment {

    private static final Logger log = LoggerFactory.getLogger(StatisticAdjustment.class);
    static final double CONSTANTE =100000;

    public static Double CoefOfVariance(List<PLTLossData> pltLossDatas) {
        if(pltLossDatas != null && !pltLossDatas.isEmpty()) {
            return stdDev(pltLossDatas) / (averageAnnualLoss(pltLossDatas) + CONSTANTE);
        } else {
            log.info("PLT EMPTY");
            return null;
        }
    }

    public static Double stdDev(List<PLTLossData> pltLossDatas) {
        if(pltLossDatas != null && !pltLossDatas.isEmpty()) {
            List<AEPMetric> aepMetrics = CalculAdjustement.getAEPMetric(pltLossDatas);
            double averageAnnualLoss =averageAnnualLoss(pltLossDatas);
            return  Math.sqrt(aepMetrics.stream().mapToDouble(value -> Math.pow(value.getLossAep() - averageAnnualLoss,2)).sum()/(CONSTANTE-1));
        } else {
            log.info("PLT EMPTY");
            return null;
        }
    }

    public static List<AEPMetric> AEPTVaRMetrics(List<AEPMetric> aepMetrics) {
        if(aepMetrics != null && !aepMetrics.isEmpty()) {
            final int[] s = {0};
            final double[] oep = {0};
            return aepMetrics.stream().map(aepMetric -> {
                s[0] = s[0] +1;
                oep[0] = oep[0] + aepMetric.getLossAep();
                return new AEPMetric(aepMetric.getFrequency(),aepMetric.getReturnPeriod() ,oep[0]/s[0] );}).collect(Collectors.toList());
        } else {
            log.info("PLT EMPTY");
            return null;
        }
    }

    public static List<OEPMetric> OEPTVaRMetrics(List<OEPMetric> oepMetrics) {
        if(oepMetrics != null && !oepMetrics.isEmpty()) {
            final int[] s = {0};
            final double[] oep = {0};
            return oepMetrics.stream().map(aepMetric -> {
                s[0] = s[0] +1;
                oep[0] = oep[0] + aepMetric.getLossOep();
                return new OEPMetric(aepMetric.getFrequency(),aepMetric.getReturnPeriod() ,oep[0]/s[0] );}).collect(Collectors.toList());
        } else {
            log.info("PLT EMPTY");
            return null;
        }
    }

    public static Double averageAnnualLoss(List<PLTLossData> pltLossDatas) {
        if(pltLossDatas != null && !pltLossDatas.isEmpty()) {
            return pltLossDatas.stream().mapToDouble(PLTLossData::getLoss).sum()/CONSTANTE;
        } else {
            log.info("PLT EMPTY");
            return null;
        }
    }
}
