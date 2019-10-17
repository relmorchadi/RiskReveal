package com.scor.rr.importBatch.processing.ylt;

import com.scor.rr.domain.entities.references.DefaultReturnPeriod;
import com.scor.rr.domain.enums.StatisticMetric;
import com.scor.rr.domain.utils.plt.EPCurve;
import com.scor.rr.domain.utils.plt.PLT;
import com.scor.rr.domain.utils.plt.SummaryStatistics;
import com.scor.rr.importBatch.processing.batch.BaseBatchBeanImpl;
import com.scor.rr.importBatch.processing.domain.PLTData;
import com.scor.rr.importBatch.processing.domain.PLTLoss;
import com.scor.rr.importBatch.processing.domain.PLTPeriod;
import com.scor.rr.importBatch.processing.domain.PLTResult;
import com.scor.rr.importBatch.processing.statistics.rms.EPCurveWriter;
import com.scor.rr.importBatch.processing.statistics.rms.EPSWriter;
import com.scor.rr.repository.references.DefaultReturnPeriodRepository;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.*;

/**
 * Created by U002629 on 14/04/2015.
 */
public class BasicEPCurveCalculator extends BaseBatchBeanImpl implements EPCurveCalculator {

    private PLTData pltData;
    private DefaultReturnPeriodRepository drpRepo;
    private EPCurveWriter writer;
    private EPSWriter writer2;

    public void setPltData(PLTData pltData) {
        this.pltData = pltData;
    }

    public void setDrpRepo(DefaultReturnPeriodRepository drpRepo) {
        this.drpRepo = drpRepo;
    }

    public void setWriter(EPCurveWriter writer) {
        this.writer = writer;
    }

    public void setWriter2(EPSWriter writer2) {
        this.writer2 = writer2;
    }

    @Override
    public Boolean runCalculation() {
        MathContext mc = MathContext.DECIMAL64;
        List<DefaultReturnPeriod> defaultRPS = drpRepo.findAll();
        Map<BigDecimal, Integer> epRPMap = new HashMap<>(defaultRPS.size());
        Map<Integer, Double> rpEPMap = new HashMap<>(defaultRPS.size());
        for (DefaultReturnPeriod drp : defaultRPS) {
            epRPMap.put(new BigDecimal(drp.getExcedanceProbability(), mc).setScale(5, RoundingMode.HALF_UP).stripTrailingZeros(), drp.getReturnPeriod());
            rpEPMap.put(drp.getReturnPeriod(), drp.getExcedanceProbability());
        }

        for (String regionPeril : pltData.getRegionPerils()) {
            final PLTLoss lossData = pltData.getLossDataForRP(regionPeril);
            final PLT purePLT = getPurePLT(regionPeril);
            SummaryStatistics s = runCalculationForLosses(lossData, purePLT.getEpCurvesByType(), epRPMap, rpEPMap);
            purePLT.getSummaryStatistics().add(s);
            writer.write(purePLT.getEpCurvesByType(), "PLT", regionPeril, lossData.getFinancialPerspective(), lossData.getCurrency(), "INTERNAL");
            writer2.write(s, "PLT", regionPeril, lossData.getFinancialPerspective(), lossData.getCurrency(), "INTERNAL");
            addMessage("EPC CALC", "EPCurves calculated OK for " + regionPeril);
        }

        return true;
    }

    @Override
    public SummaryStatistics runCalculationForLosses(PLTLoss lossData, Map<String, EPCurve> epCurvesByType) {
        MathContext mc = MathContext.DECIMAL64;
        List<DefaultReturnPeriod> defaultRPS = drpRepo.findAll();
        Map<BigDecimal, Integer> epRPMap = new HashMap<>(defaultRPS.size());
        Map<Integer, Double> rpEPMap = new HashMap<>(defaultRPS.size());
        for (DefaultReturnPeriod drp : defaultRPS) {
            epRPMap.put(new BigDecimal(drp.getExcedanceProbability(), mc).setScale(5, RoundingMode.HALF_UP).stripTrailingZeros(), drp.getReturnPeriod());
            rpEPMap.put(drp.getReturnPeriod(), drp.getExcedanceProbability());
        }
        return runCalculationForLosses(lossData, epCurvesByType, epRPMap, rpEPMap);
    }

    @Override
    public SummaryStatistics runCalculationForLosses(PLTLoss lossData, Map<String, EPCurve> epCurvesByType, Map<BigDecimal, Integer> epRPMap, Map<Integer, Double> rpEPMap) {
        final int nYears = 100000;
        MathContext mc = MathContext.DECIMAL64;
        List<Double> lossesEEF = new ArrayList<Double>();
        List<Double> lossesOEP = new ArrayList<>();
        List<Double> lossesAEP = new ArrayList<>();

        double sumOEP = 0.0d;
        double sumAEP = 0.0d;

        for (PLTPeriod pltPeriod : lossData.getPeriods()) {
            double periodMax = 0.0d;
            double periodSum = 0.0d;
            for (PLTResult result : pltPeriod.getResults()) {
                final double loss = result.getLoss();
                periodSum += loss;
                if (periodMax < loss)
                    periodMax = loss;
                if (!lossesEEF.contains(loss)) {
                    lossesEEF.add(loss);
                }
            }
            if (!lossesOEP.contains(periodMax)) {
                lossesOEP.add(periodMax);
                sumOEP += periodMax;
            }
            if (!lossesAEP.contains(periodSum)) {
                lossesAEP.add(periodSum);
                sumAEP += periodSum;
            }
        }
        Collections.sort(lossesEEF);
        Collections.sort(lossesOEP);
        Collections.sort(lossesAEP);

        List<Integer> rankEEF = new ArrayList<>();
        List<Integer> rankOEP = new ArrayList<>();
        List<Integer> rankOEPTCE = new ArrayList<>();
        List<Integer> rankAEP = new ArrayList<>();
        List<Integer> rankAEPTCE = new ArrayList<>();

        int nMaxSize = Math.max(lossesEEF.size(), lossesOEP.size());
        int ijEEF = 0, ijOEP = 0;
        for (int j = 0; j < nMaxSize; ++j) {
            if (j < lossesEEF.size()) {
                rankEEF.add(j + 1);
            }
            if (j < lossesOEP.size()) {
                ijOEP = lossesOEP.size() - j;
                rankOEP.add(j + 1);
                rankAEP.add(j + 1);
                rankOEPTCE.add(ijOEP);
                rankAEPTCE.add(ijOEP);
            }
        }

        EPCurve curveEEF = new EPCurve();
        EPCurve curveOEP = new EPCurve();
        EPCurve curveAEP = new EPCurve();
        EPCurve curveOEPTCE = new EPCurve();
        EPCurve curveAEPTCE = new EPCurve();
        curveEEF.setStatisticMetricId(2);
        curveAEP.setStatisticMetricId(0);
        curveOEP.setStatisticMetricId(1);
        curveAEPTCE.setStatisticMetricId(10);
        curveOEPTCE.setStatisticMetricId(11);

        final BigDecimal nBigYear = new BigDecimal(nYears, mc);
        final BigDecimal purePremium = BigDecimal.valueOf(sumAEP).divide(nBigYear, mc);

        BigDecimal aalSum = BigDecimal.ZERO;

        for (int i = 0; i < nMaxSize; ++i) {
            int iiOEP = lossesOEP.size() - i - 1;
            int iiEEF = lossesEEF.size() - i - 1;
            if (i < lossesEEF.size()) {
                // EEF
                final BigDecimal myRankEEF = new BigDecimal(rankEEF.get(i), mc);
                BigDecimal epEef = myRankEEF.divide(nBigYear, mc).setScale(5, RoundingMode.HALF_UP).stripTrailingZeros();
                final Integer rpEef = epRPMap.get(epEef);
                double eefLoss = lossesEEF.get(iiEEF);
                BigDecimal rpEefBig = BigDecimal.ONE.divide(epEef, mc).setScale(20, RoundingMode.HALF_UP).stripTrailingZeros();
                if (rpEef != null) {
                    curveEEF.addLoss(rpEPMap.get(rpEef), rpEef, eefLoss);
                }
            }
            if (i < lossesOEP.size()) {
                final BigDecimal myRankOEP = new BigDecimal(rankOEP.get(i), mc);
                final BigDecimal myRankOEPTCE = new BigDecimal(rankOEPTCE.get(i), mc);
                final BigDecimal myRankAEP = new BigDecimal(rankAEP.get(i), mc);
                final BigDecimal myRankAEPTCE = new BigDecimal(rankAEPTCE.get(i), mc);

                // frequency
                BigDecimal epOep = myRankOEP.divide(nBigYear, mc).setScale(5, RoundingMode.HALF_UP).stripTrailingZeros();
                BigDecimal epOepTce = myRankOEPTCE.divide(nBigYear, mc).setScale(5, RoundingMode.HALF_UP).stripTrailingZeros();
                BigDecimal epAep = myRankAEP.divide(nBigYear, mc).setScale(5, RoundingMode.HALF_UP).stripTrailingZeros();
                BigDecimal epAepTce = myRankAEPTCE.divide(nBigYear, mc).setScale(5, RoundingMode.HALF_UP).stripTrailingZeros();

                final Integer rpOEP = epRPMap.get(epOep);
                Integer rpOEPTCE = epRPMap.get(epOepTce);
                Integer rpAEP = epRPMap.get(epAep);
                Integer rpAEPTCE = epRPMap.get(epAepTce);

                double lossOEP = lossesOEP.get(iiOEP); // get biggest first, from last to first
                double lossAEP = lossesAEP.get(iiOEP);

                if (rpOEP != null) {
                    curveOEP.addLoss(rpEPMap.get(rpOEP), rpOEP, lossOEP);
                }
                if (rpOEPTCE != null) {
                    final double oepTCEValue = BigDecimal.valueOf(sumOEP).divide(myRankOEPTCE, mc).doubleValue();
                    curveOEPTCE.addLoss(rpEPMap.get(rpOEPTCE), rpOEPTCE, oepTCEValue);
                }
                if (rpAEP != null) {
                    curveAEP.addLoss(rpEPMap.get(rpAEP), rpAEP, lossAEP);
                }
                if (rpAEPTCE != null) {
                    final double aepTCEValue = BigDecimal.valueOf(sumAEP).divide(myRankAEPTCE, mc).doubleValue();
                    curveAEPTCE.addLoss(rpEPMap.get(rpAEPTCE), rpAEPTCE, aepTCEValue);
                }
                // rp and ep are couped in a predefined file default_return_periods_ref.csv
                sumOEP -= lossesOEP.get(i); // get first value, at the beginning
                sumAEP -= lossesAEP.get(i);

                aalSum = aalSum.add(BigDecimal.valueOf(lossAEP).subtract(purePremium, mc).pow(2, mc), mc);
            }
        }

        SummaryStatistics s;
        try {
            double stdDev = Math.sqrt(aalSum.divide(nBigYear.subtract(BigDecimal.ONE), mc).doubleValue());
            double cov = BigDecimal.valueOf(stdDev).divide(purePremium, mc).doubleValue();

            s = new SummaryStatistics(1, purePremium.doubleValue(), stdDev, cov, curveAEP.getLossAmountsByReturnPeriod().get(100), curveAEP.getLossAmountsByReturnPeriod().get(250), curveAEP.getLossAmountsByReturnPeriod().get(500));
        } catch (Exception e) {
            s = new SummaryStatistics(1, purePremium.doubleValue(), 0.0d, 0.0d, 0.0d, 0.0d, 0.0d);
        }

        epCurvesByType.put(StatisticMetric.EEF.toString(), curveEEF);
        epCurvesByType.put(StatisticMetric.OEP.toString(), curveOEP);
        epCurvesByType.put(StatisticMetric.TVAR_OEP.toString(), curveOEPTCE);
        epCurvesByType.put(StatisticMetric.AEP.toString(), curveAEP);
        epCurvesByType.put(StatisticMetric.TVAR_AEP.toString(), curveAEPTCE);

        return s;
    }

}
