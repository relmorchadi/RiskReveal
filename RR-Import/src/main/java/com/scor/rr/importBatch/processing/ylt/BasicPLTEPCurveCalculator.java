package com.scor.rr.importBatch.processing.ylt;

import com.scor.rr.domain.entities.ihub.RRFinancialPerspective;
import com.scor.rr.domain.entities.plt.PLTConverterProgress;
import com.scor.rr.domain.entities.plt.ScorPLTHeader;
import com.scor.rr.domain.entities.references.DefaultReturnPeriod;
import com.scor.rr.domain.entities.rms.ProjectImportAssetLog;
import com.scor.rr.domain.entities.rms.RmsAnalysis;
import com.scor.rr.domain.entities.stat.RREPCurve;
import com.scor.rr.domain.entities.stat.RRStatisticHeader;
import com.scor.rr.domain.entities.stat.RRSummaryStatistic;
import com.scor.rr.domain.enums.AdjustmentType;
import com.scor.rr.domain.enums.StatDataType;
import com.scor.rr.domain.enums.StatisticMetric;
import com.scor.rr.domain.utils.plt.PLTEPCurve;
import com.scor.rr.domain.utils.plt.PLTSummaryStatistic;
import com.scor.rr.importBatch.processing.batch.BaseBatchBeanImpl;
import com.scor.rr.importBatch.processing.statistics.rms.EPCurveWriter;
import com.scor.rr.importBatch.processing.statistics.rms.EPSWriter;
import com.scor.rr.importBatch.processing.treaty.PLTBundle;
import com.scor.rr.importBatch.processing.treaty.TransformationBundle;
import com.scor.rr.importBatch.processing.treaty.TransformationPackage;
import com.scor.rr.importBatch.processing.treaty.loss.PLTLossData;
import com.scor.rr.importBatch.processing.treaty.services.TransformationUtils;
import com.scor.rr.importBatch.processing.ylt.meta.PLTPublishStatus;
import com.scor.rr.importBatch.processing.ylt.meta.XLTOT;
import com.scor.rr.repository.rms.ProjectImportAssetLogRepository;
import com.scor.rr.repository.stat.RRStatisticHeaderRepository;
import com.scor.rr.service.omega.DAOService;
import com.scor.rr.utils.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.*;

/**
 * Created by u004119 on 11/05/2016.
 */
public class BasicPLTEPCurveCalculator extends BaseBatchBeanImpl implements PLTEPCurveCalculator {

    private static boolean DBG = false;
    private Logger log = LoggerFactory.getLogger(PLTEPCurveCalculator.class);

    @Autowired
    private DAOService daoService;
    private TransformationPackage transformationPackage;
    private EPCurveWriter epCurveWriter;
    private EPSWriter epsWriter;
    private PLTReader pltReader;

    private Map<BigDecimal, Integer> epRPMap;
    private Map<Integer, Double> rpEPMap;

    private void extractEPRPMap() {
        if (epRPMap != null && rpEPMap != null) {
            return;
        }
        MathContext mc = MathContext.DECIMAL64;
        List<DefaultReturnPeriod> defaultRPS = daoService.findDefaultReturnPeriods();
        epRPMap = new HashMap<>(defaultRPS.size());
        rpEPMap = new HashMap<>(defaultRPS.size());
        for (DefaultReturnPeriod drp : defaultRPS) {
            epRPMap.put(new BigDecimal(drp.getExcedanceProbability(), mc).setScale(5, RoundingMode.HALF_UP).stripTrailingZeros(), drp.getReturnPeriod());
            rpEPMap.put(drp.getReturnPeriod(), drp.getExcedanceProbability());
        }
    }

    @Autowired
    private ProjectImportAssetLogRepository projectImportAssetLogRepository;

    @Autowired
    private RRStatisticHeaderRepository rrStatisticHeaderRepository;

    @Override
    public void runCalculationForImport() {
        log.debug("Starting runCalculationForImport");

        Date startDate = new Date();

        extractEPRPMap();
        for (TransformationBundle bundle : transformationPackage.getBundles()) {

            // start new step (import progress) : step 15 CALCULATE_EPCURVE for this analysis in loop of many analysis :
            ProjectImportAssetLog projectImportAssetLogA = new ProjectImportAssetLog();
            projectImportAssetLogA.setProjectId(transformationPackage.getProjectId());
            projectImportAssetLogA.setStepId(15);
            projectImportAssetLogA.setStepName(Step.getStepNameFromStepIdForAnalysis(projectImportAssetLogA.getStepId()));
            projectImportAssetLogA.setStartDate(startDate);
            projectImportAssetLogA.setProjectImportLogId(bundle.getProjectImportLogAnalysisId());
            // --------------------------------------------------------------------


            if (bundle.getPltBundles() == null) {
                if (DBG) log.error("ERROR in ELT {}, no PLTs found", bundle.getConformedRRLT().getRrLossTableId());

                // must ask Viet same check error on step 14
                projectImportAssetLogA.getErrorMessages().add(String.format("ERROR in ELT %s, no PLTs found", bundle.getConformedRRLT().getRrLossTableId()));
                Date endDate = new Date();
                projectImportAssetLogA.setEndDate(endDate);
                projectImportAssetLogRepository.save(projectImportAssetLogA);
                log.info("Finish import progress STEP 15 : CALCULATE_EPCURVE for analysis: {}", bundle.getSourceResult().getSourceResultId());

                continue;
            }

            for (PLTBundle pltBundle : bundle.getPltBundles()) {

                if (pltBundle.getHeader100k() == null || pltBundle.getHeader100k().getPltLossDataFile() == null) {
                    continue;
                }

                PLTConverterProgress pltConverterProgress = daoService.findPLTConverterProgressBy(pltBundle.getHeader100k().getScorPLTHeaderId());
                pltConverterProgress.setStartCalcStat(new Date());
                daoService.persistPLTConverterProgress(pltConverterProgress);
                List<PLTLossData> lossData = null;
                try {
                    lossData = pltReader.readPLTLossDataV2(pltBundle.getHeader100k().getPltLossDataFile());
                } catch (Exception e) { // must ask Viet
                    log.error("Error: {}", e.getMessage());
                    lossData = null;
                }

                if (lossData == null) {
                    continue;
                }

                if (!lossData.isEmpty()) {
                    extractEPCurveStats(bundle.getRmsAnalysis(), pltBundle.getHeader100k(), lossData);
                }
                pltConverterProgress.setEndCalcStat(new Date());
                daoService.persistPLTConverterProgress(pltConverterProgress);

                // finish step 15 CALCULATE_EPCURVE for one analysis in loop for of many analysis
                Date endDate = new Date();
                projectImportAssetLogA.setEndDate(endDate);
                projectImportAssetLogRepository.save(projectImportAssetLogA);
                log.info("Finish import progress STEP 15 : CALCULATE_EPCURVE for analysis: {}", bundle.getSourceResult().getSourceResultId());
            }

        }
        log.debug("runCalculationForImport completed");
    }

    @Override
    public void extractEPCurveStats(RmsAnalysis rmsAnalysis, ScorPLTHeader pltHeader, List<PLTLossData> sortedLossData) {
//        if (pltHeader.getId() == null) {
//            daoService.getMongoDBSequence().nextSequenceId(pltHeader);
//        }
        RRSummaryStatistic summaryStatistic = new RRSummaryStatistic(); // PLT
        Map<StatisticMetric, List<RREPCurve>> metricToEPCurve = new HashMap<>();


        log.debug("runCalculationForLosses for ptlHearer id {} - {} simulation periods", pltHeader.getScorPLTHeaderId(), pltHeader.getPltSimulationPeriods());
        runCalculationForLosses(pltHeader.getPltSimulationPeriods(), sortedLossData, epRPMap, rpEPMap, summaryStatistic, metricToEPCurve);

        List<RRStatisticHeader> rrStatisticHeaderList = new ArrayList<>();
        for (Map.Entry<StatisticMetric, List<RREPCurve>> entry : metricToEPCurve.entrySet()) {
            RRStatisticHeader rrStatisticHeader = new RRStatisticHeader();
            rrStatisticHeader.setProjectId(transformationPackage.getProjectId());
            rrStatisticHeader.setLossDataType(StatDataType.STAT_DATA_TYPE_PLT.toString());
            rrStatisticHeader.setLossTableId(pltHeader.getScorPLTHeaderId());
            rrStatisticHeader.getStatisticData().setSummaryStatistic(summaryStatistic);
            rrStatisticHeader.getStatisticData().setStatisticMetric(entry.getKey());
            rrStatisticHeader.getStatisticData().setEpCurves(entry.getValue());
            rrStatisticHeader.setFinancialPerspective(pltHeader.getFinancialPerspective() != null ? new RRFinancialPerspective(pltHeader.getFinancialPerspective()) : null);
            rrStatisticHeaderList.add(rrStatisticHeader);
        }

        pltHeader.setPltStatisticList(rrStatisticHeaderList);
        rrStatisticHeaderRepository.saveAll(rrStatisticHeaderList);
        daoService.persistScorPLTHeader(pltHeader);

        String epCurveFilename = makePLTEPCurveFilename(
                pltHeader.getCreationDate(),
                pltHeader.getRegionPeril().getRegionPerilCode(),
                pltHeader.getFinancialPerspective().getCode(),
                pltHeader.getCurrency().getCode(),
                XLTOT.TARGET,
                pltHeader.getTargetRAP().getTargetRAPId(),
                pltHeader.getPltSimulationPeriods(),
                PLTPublishStatus.PURE,
                0, // pure PLT
                pltHeader.getScorPLTHeaderId(),
                getFileExtension());

        String sumstatFilename = makePLTSummaryStatFilename(
                pltHeader.getCreationDate(),
                pltHeader.getRegionPeril().getRegionPerilCode(),
                pltHeader.getFinancialPerspective().getCode(),
                pltHeader.getCurrency().getCode(),
                XLTOT.TARGET,
                pltHeader.getTargetRAP().getTargetRAPId(),
                pltHeader.getPltSimulationPeriods(),
                PLTPublishStatus.PURE,
                0, // pure PLT
                pltHeader.getScorPLTHeaderId(),
                getFileExtension());

        epCurveWriter.writePLTEPCurves(metricToEPCurve, epCurveFilename);
        epsWriter.writePLTSummaryStatistics(summaryStatistic, sumstatFilename);

        if (DBG) {
            for (RRStatisticHeader rrStatisticHeader : rrStatisticHeaderList) {
                log.info("Saving RRStatisticHeader {} (RREPCurves & SumStat) for PLT {}, RRLT {}", rrStatisticHeader.getRrStatisticHeaderId(), pltHeader.getScorPLTHeaderId(), pltHeader.getRrLossTableId());
            }
        }

        // TODO - ignore
        // testGenerateCSV(rmsAnalysis, pltHeader, sortedLossData, metricToEPCurve, summaryStatistic);
    }

    private void testGenerateCSV(RmsAnalysis rmsAnalysis, ScorPLTHeader scorPLTHeader, List<PLTLossData> sortedLossData, Map<StatisticMetric, List<PLTEPCurve>> metricToEPCurve, PLTSummaryStatistic summaryStatistic) {
        String path = null;
        String pltFilename = makePLTFilename(scorPLTHeader, rmsAnalysis, scorPLTHeader.getPltSimulationPeriods().toString());

        TransformationUtils.writePLT(sortedLossData, path, pltFilename);
        TransformationUtils.writeEPCurve(metricToEPCurve, path, AdjustmentType.NONE.toString(), pltFilename);
        TransformationUtils.writeSummaryStats(summaryStatistic, path, pltFilename);
    }

    private String makePLTFilename(ScorPLTHeader pltHeader, RmsAnalysis rmsAnalysis, String nPeriod) {
        return makeFileName(pltHeader.getProject().getProjectId(),
                pltHeader.getScorPLTHeaderId(),
                rmsAnalysis.getRdmName(),
                rmsAnalysis.getRdmId(),
                rmsAnalysis.getAnalysisId(),
                nPeriod);
    }
    private String makeFileName(String projectId, String pltHeaderId, String rdmName, Long rdmId, String analysisId, String nPeriodVer) {
        String separator = "_";
        String extension = ".bin";
        String fileName = "PLT".concat(separator)
                .concat(projectId).concat(separator)
                .concat(pltHeaderId).concat(separator)
                .concat(rdmName).concat(separator)
                .concat(String.valueOf(rdmId)).concat(separator)
                .concat(analysisId).concat(separator)
//                .concat(fp).concat(separator)
                .concat(nPeriodVer)
                .concat(extension);
        return fileName;
    }

    @Override
    // PLTEPC
    public void runCalculationForLosses(int nYears, List<PLTLossData> sortedLossData, Map<BigDecimal, Integer> epRPMap, Map<Integer, Double> rpEPMap, RRSummaryStatistic pltSummaryStatistic, Map<StatisticMetric, List<RREPCurve>> metricToEPCurve) {
        if (!TransformationUtils.isSortedReversely(sortedLossData)) {
            log.info("Resorting data due to float conversion");
            TransformationUtils.sortReverse(sortedLossData);
        }

        MathContext mc = MathContext.DECIMAL64;
        List<Double> lossesEEF = new ArrayList<>(); // descending order

        Map<Integer, Double> periodToOEPLoss = new LinkedHashMap<>(); // no need to sort
        Map<Integer, Double> periodToAEPLoss = new HashMap<>(); // necessary to sort

        double sumAEP = 0.0d;

        //if (DBG) log.info("Preparing and sorting input losses, total events = {}", sortedLossData.size());
        log.debug("Preparing and sorting input losses, total events = {}", sortedLossData.size());
        int countOEP = 0;
        int countAEPUpdate = 0;
        int countAEP = 0;
        int countEEF = 0;
        for (PLTLossData pltLossData : sortedLossData) {
            countEEF++;
            double loss = pltLossData.getLoss();
            // EEF
            lossesEEF.add(loss);
            // O
            int period = pltLossData.getSimPeriod();
            if (!periodToOEPLoss.containsKey(period)) {
                countOEP++;
                periodToOEPLoss.put(period, loss);
            }
            // AEP
            if (periodToAEPLoss.containsKey(period)) {
                Double prevLoss = periodToAEPLoss.get(period);
                periodToAEPLoss.put(period, prevLoss + loss);
                countAEPUpdate++;
            } else {
                countAEP++;
                periodToAEPLoss.put(period, pltLossData.getLoss());
            }
            sumAEP += loss;
        }
        //if (DBG) log.info("Ranking losses, countEEF {}, countOEP {}, countAEP {}, countAEPUpdate {}", countEEF, countOEP, countAEP, countAEPUpdate);
        log.debug("Ranking losses, countEEF {}, countOEP {}, countAEP {}, countAEPUpdate {}", countEEF, countOEP, countAEP, countAEPUpdate);

        List<Double> lossesOEP = new ArrayList<>(periodToOEPLoss.values());
        List<Double> lossesAEP = new ArrayList<>(periodToAEPLoss.values());
        Collections.sort(lossesAEP, Collections.<Double>reverseOrder());

        if (DBG) log.info("Ranking losses");

        // all before are PLTEPCurve
        List<RREPCurve> curvesEEF = new ArrayList<>();
        List<RREPCurve> curvesOEP = new ArrayList<>();
        List<RREPCurve> curvesAEP = new ArrayList<>();
        List<RREPCurve> curvesOEPTCE = new ArrayList<>();
        List<RREPCurve> curvesAEPTCE = new ArrayList<>();
        List<RREPCurve> curvesCEP = new ArrayList<>();

        final BigDecimal nBigYear = new BigDecimal(nYears, mc);
        final BigDecimal purePremium = BigDecimal.valueOf(sumAEP).divide(nBigYear, mc);

        BigDecimal aalSum = BigDecimal.ZERO;
        int nLossesOEP = lossesOEP.size();
        int nLossesAEP = lossesAEP.size();
        int nLossesEEF = lossesEEF.size();
        int nLossesMax = Math.max(nLossesEEF, Math.max(nLossesOEP, nLossesAEP));
        BigDecimal nBigEvCount = new BigDecimal(nLossesEEF, mc);

        // CEP
        int scaleCEP = (int) Math.ceil(Math.log10(nLossesEEF));
        if (DBG) log.info("scaleCEP = {}", scaleCEP);

        //if (DBG) log.info("Calculating curves, lossEEFSize = {}, lossOEPSize = {}, lossAEPSize = {}", nLossesEEF, nLossesOEP, nLossesAEP);
        log.debug("Calculating curves, lossEEFSize = {}, lossOEPSize = {}, lossAEPSize = {}", nLossesEEF, nLossesOEP, nLossesAEP);
        int rank = 0; double lossOEPTCE = 0; double lossAEPTCE = 0;
        for (int i = 0; i < nLossesMax; ++i) {
            rank++;
            final BigDecimal bigRank = new BigDecimal(rank, mc);
            BigDecimal epEEF = bigRank.divide(nBigYear, mc).setScale(5, RoundingMode.HALF_UP).stripTrailingZeros();

            // EEF
            final Integer rpEEF = epRPMap.get(epEEF);
            if (i < nLossesEEF) {
                Double lossEEF = lossesEEF.get(i);
                if (rpEEF != null) {
                    curvesEEF.add(new RREPCurve(rpEEF, rpEPMap.get(rpEEF), lossEEF));
                }
            }
            // TODO - remove - test
//            Double lossEEF = lossesEEF.get(i);
//            Double rpEEF = 1d / epEEF.doubleValue();
//            curvesEEF.add(new PLTEPCurve(rpEEF.intValue(), epEEF.doubleValue(), lossEEF));

            // CEP
            if (i < nLossesEEF) {
                BigDecimal epCEP = bigRank.divide(nBigEvCount, mc).setScale(scaleCEP, RoundingMode.HALF_UP).stripTrailingZeros();
                final Integer rpCEP = epRPMap.get(epCEP);
                if (rpCEP != null) {
                    curvesCEP.add(new RREPCurve(rpCEP, rpEPMap.get(rpCEP), lossesEEF.get(i)));
                }
            }

            if (i < nLossesOEP) {
                Double lossOEP = lossesOEP.get(i); // get biggest first, from last to first -- > first to last
                lossOEPTCE += lossOEP;
                if (rpEEF != null) {
                    // OEP
                    curvesOEP.add(new RREPCurve(rpEEF, rpEPMap.get(rpEEF), lossOEP)); // PLTEPC
                    // OEP-TCE
                    curvesOEPTCE.add(new RREPCurve(rpEEF, rpEPMap.get(rpEEF), lossOEPTCE / rank)); // PLTEPC
                }

                // TODO - test - remove
//                Double lossOEP = lossesOEP.get(i);
//                lossOEPTCE += lossOEP;
//                curvesOEP.add(new PLTEPCurve(rpEEF.intValue(), epEEF.doubleValue(), lossOEP));
//                curvesOEPTCE.add(new PLTEPCurve(rpEEF.intValue(), epEEF.doubleValue(), 1d * lossOEPTCE / rank));
            }

            if (i < nLossesAEP) {
                Double lossAEP = lossesAEP.get(i);
                lossAEPTCE += lossAEP;
                if (rpEEF != null) {
                    // AEP
                    curvesAEP.add(new RREPCurve(rpEEF, rpEPMap.get(rpEEF), lossAEP)); // PLTEPC
                    // AEP-TCE
                    curvesAEPTCE.add(new RREPCurve(rpEEF, rpEPMap.get(rpEEF), lossAEPTCE / rank)); // PLTEPC
                }

//                // TODO - test - remove
//                Double lossAEP = lossesAEP.get(i);
//                lossAEPTCE += lossAEP;
//                curvesAEP.add(new PLTEPCurve(rpEEF.intValue(), epEEF.doubleValue(), lossAEP));
//                curvesAEPTCE.add(new PLTEPCurve(rpEEF.intValue(), epEEF.doubleValue(), 1d * lossAEPTCE / rank));

                aalSum = aalSum.add(BigDecimal.valueOf(lossAEP).subtract(purePremium, mc).pow(2, mc), mc);
            }
        }

        //if (DBG) log.info("Calculating statistics");
        log.debug("Calculating statistics");
        try {
            double stdDev = Math.sqrt(aalSum.divide(nBigYear.subtract(BigDecimal.ONE), mc).doubleValue());
            double cov = purePremium.doubleValue() != 0 ? BigDecimal.valueOf(stdDev).divide(purePremium, mc).doubleValue() : 0.0;
            pltSummaryStatistic.setCov(cov);
            pltSummaryStatistic.setPurePremium(purePremium.doubleValue());
            pltSummaryStatistic.setStandardDeviation(stdDev);
        } catch (Exception e) {
            e.printStackTrace();
        }

        metricToEPCurve.put(StatisticMetric.OEP, curvesOEP);
        metricToEPCurve.put(StatisticMetric.AEP, curvesAEP);
        metricToEPCurve.put(StatisticMetric.TVAR_OEP, curvesOEPTCE);
        metricToEPCurve.put(StatisticMetric.TVAR_AEP, curvesAEPTCE);
        metricToEPCurve.put(StatisticMetric.EEF, curvesEEF);
        metricToEPCurve.put(StatisticMetric.CEP, curvesCEP);
    }


    public TransformationPackage getTransformationPackage() {
        return transformationPackage;
    }

    public void setTransformationPackage(TransformationPackage transformationPackage) {
        this.transformationPackage = transformationPackage;
    }

    public DAOService getDaoService() {
        return daoService;
    }

    public void setDaoService(DAOService daoService) {
        this.daoService = daoService;
    }

    public EPCurveWriter getEpCurveWriter() {
        return epCurveWriter;
    }

    public void setEpCurveWriter(EPCurveWriter epCurveWriter) {
        this.epCurveWriter = epCurveWriter;
    }

    public EPSWriter getEpsWriter() {
        return epsWriter;
    }

    public void setEpsWriter(EPSWriter epsWriter) {
        this.epsWriter = epsWriter;
    }

    public PLTReader getPltReader() {
        return pltReader;
    }

    public void setPltReader(PLTReader pltReader) {
        this.pltReader = pltReader;
    }
}
