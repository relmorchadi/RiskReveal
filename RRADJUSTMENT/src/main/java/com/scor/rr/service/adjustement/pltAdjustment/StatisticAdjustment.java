package com.scor.rr.service.adjustement.pltAdjustment;

import com.scor.rr.domain.dto.AEPMetric;
import com.scor.rr.domain.dto.OEPMetric;
import com.scor.rr.domain.dto.adjustement.loss.PLTLossData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

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
            return  Math.sqrt(pltLossDatas.stream().mapToDouble(PLTLossData::getLoss).sum()/(CONSTANTE-1));
        } else {
            log.info("PLT EMPTY");
            return null;
        }
    }

    public static Double AEPTVaRMetrics(List<AEPMetric> aepMetrics) {
        if(aepMetrics != null && !aepMetrics.isEmpty()) {
            int s = 0;
            double oep = 0;
            return aepMetrics.stream().mapToDouble(oepMetric -> (oep + oepMetric.getLossAep()) / (s + 1)).sum();
        } else {
            log.info("PLT EMPTY");
            return null;
        }
    }

    public static Double OEPTVaRMetrics(List<OEPMetric> oepMetrics) {
        if(oepMetrics != null && !oepMetrics.isEmpty()) {
            int s = 0;
            double oep = 0;
            return oepMetrics.stream().mapToDouble(oepMetric -> (oep + oepMetric.getLossOep()) / (s + 1)).sum();
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
