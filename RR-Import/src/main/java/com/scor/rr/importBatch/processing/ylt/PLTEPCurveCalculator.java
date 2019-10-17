package com.scor.rr.importBatch.processing.ylt;

import com.scor.rr.domain.entities.plt.ScorPLTHeader;
import com.scor.rr.domain.entities.rms.RmsAnalysis;
import com.scor.rr.domain.entities.stat.RREPCurve;
import com.scor.rr.domain.entities.stat.RRSummaryStatistic;
import com.scor.rr.domain.enums.StatisticMetric;
import com.scor.rr.importBatch.processing.treaty.loss.PLTLossData;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by u004119 on 11/05/2016.
 */
public interface PLTEPCurveCalculator {

    // PLTEPC
    void runCalculationForLosses(int nYears, List<PLTLossData> sortedLossData, Map<BigDecimal, Integer> epRPMap, Map<Integer, Double> rpEPMap, RRSummaryStatistic pltSummaryStatistic, Map<StatisticMetric, List<RREPCurve>> metricToEPCurve);

    void runCalculationForImport();

    void extractEPCurveStats(RmsAnalysis rmsAnalysis, ScorPLTHeader pltHeader, List<PLTLossData> sortedLossData);

}
