package com.scor.rr.importBatch.processing.ylt;

import com.scor.rr.domain.utils.plt.EPCurve;
import com.scor.rr.domain.utils.plt.SummaryStatistics;
import com.scor.rr.importBatch.processing.domain.PLTLoss;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by U002629 on 14/04/2015.
 */
public interface EPCurveCalculator {
    Boolean runCalculation();

    SummaryStatistics runCalculationForLosses(PLTLoss lossData, Map<String, EPCurve> epCurvesByType);

    SummaryStatistics runCalculationForLosses(PLTLoss lossData, Map<String, EPCurve> epCurvesByType, Map<BigDecimal, Integer> epRPMap, Map<Integer, Double> rpEPMap);
}
