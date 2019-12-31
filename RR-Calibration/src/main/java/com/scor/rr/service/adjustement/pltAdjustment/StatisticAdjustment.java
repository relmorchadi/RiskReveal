package com.scor.rr.service.adjustement.pltAdjustment;

import com.scor.rr.domain.dto.EPMetric;
import com.scor.rr.domain.dto.EPMetricPoint;
import com.scor.rr.domain.dto.adjustement.loss.PLTLossData;
import com.scor.rr.domain.enums.StatisticMetric;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatisticAdjustment {

    private static final Logger log = LoggerFactory.getLogger(StatisticAdjustment.class);
    private static final double CONSTANTE =100000;

    public static double CoefOfVariance(List<PLTLossData> pltLossDatas) {
        if(pltLossDatas != null && !pltLossDatas.isEmpty())
            return stdDev(pltLossDatas) / averageAnnualLoss(pltLossDatas);
        else {
            log.info("PLT EMPTY");
            return 0;
        }
    }

    public static double stdDev(List<PLTLossData> pltLossDatas) {
        if(pltLossDatas != null && !pltLossDatas.isEmpty()) {
            List<EPMetricPoint> aepMetrics = CalculateAdjustmentService.getAEPMetric(pltLossDatas).getEpMetricPoints();
            if(aepMetrics != null) {
                double averageAnnualLoss;
                averageAnnualLoss = averageAnnualLoss(pltLossDatas);
                return Math.sqrt(aepMetrics.stream().mapToDouble(value -> Math.pow(value.getLoss() - averageAnnualLoss, 2)).sum() / (CONSTANTE - 1));
            } else {
                log.info("AEP EMPTY");
                return 0;
            }
        } else {
            log.info("PLT EMPTY");
            return 0;
        }
    }

    public static EPMetric AEPTVaRMetrics(List<EPMetricPoint> aepMetrics) {
        if(aepMetrics != null && !aepMetrics.isEmpty()) {
            final int[] s = {0};
            final double[] oep = {0};
            return new EPMetric(StatisticMetric.TVAR_AEP,
                    aepMetrics.stream().map(aepMetric -> {
                s[0] = s[0] +1;
                oep[0] = oep[0] + aepMetric.getLoss();
                return new EPMetricPoint(aepMetric.getFrequency(),aepMetric.getReturnPeriod() ,oep[0]/s[0], aepMetric.getRank() );}).collect(Collectors.toList()));
        } else {
            log.info("PLT EMPTY");
            return null;
        }
    }

    public static EPMetric OEPTVaRMetrics(List<EPMetricPoint> oepMetrics) {
        if(oepMetrics != null && !oepMetrics.isEmpty()) {
            final int[] s = {0};
            final double[] oep = {0};
            return new EPMetric(StatisticMetric.TVAR_OEP,
                    oepMetrics.stream().map(oepMetric -> {
                s[0] = s[0] +1;
                oep[0] = oep[0] + oepMetric.getLoss();
                return new EPMetricPoint(oepMetric.getFrequency(),oepMetric.getReturnPeriod() ,oep[0]/s[0], oepMetric.getRank() );}).collect(Collectors.toList()));
        } else {
            log.info("PLT EMPTY");
            return null;
        }
    }

    public static double averageAnnualLoss(List<PLTLossData> pltLossDatas) {
        if(pltLossDatas != null && !pltLossDatas.isEmpty()) {
            return pltLossDatas.stream().mapToDouble(PLTLossData::getLoss).sum()/CONSTANTE;
        } else {
            log.info("PLT EMPTY");
            return 0;
        }
    }
}
