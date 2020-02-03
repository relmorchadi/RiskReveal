package com.scor.rr.service.fileBasedImport.batch;

import com.scor.rr.domain.*;
import com.scor.rr.domain.dto.PLTLossData;
import com.scor.rr.domain.enums.RRLossTableType;
import com.scor.rr.domain.enums.StatisticMetric;
import com.scor.rr.repository.LossDataHeaderEntityRepository;
import com.scor.rr.repository.SummaryStatisticHeaderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.*;

@Service
@StepScope
public class CalculateEPCEPSForSourcePLTService {

    private static final Logger log = LoggerFactory.getLogger(CalculateEPCEPSForSourcePLTService.class);

    private static boolean DBG = false;

    @Autowired
    private LossDataHeaderEntityRepository lossDataHeaderEntityRepository;

    @Autowired
    private SummaryStatisticHeaderRepository summaryStatisticHeaderRepository;

//    @Autowired
//    private DAOService daoService;

    private Map<BigDecimal, Integer> epRPMap;
    private Map<Integer, Double> rpEPMap;

    private TransformationPackageNonRMS transformationPackage;

    private void extractEPRPMap() {
        if (epRPMap != null && rpEPMap != null) {
            return;
        }
        MathContext mc = MathContext.DECIMAL64;
        List<DefaultReturnPeriodEntity> defaultRPS = daoService.findDefaultReturnPeriods();
        epRPMap = new HashMap<>(defaultRPS.size());
        rpEPMap = new HashMap<>(defaultRPS.size());
        for (DefaultReturnPeriodEntity drp : defaultRPS) {
            epRPMap.put(new BigDecimal(drp.getExcedanceProbability(), mc).setScale(5, RoundingMode.HALF_UP).stripTrailingZeros(), drp.getReturnPeriod());
            rpEPMap.put(drp.getReturnPeriod(), drp.getExcedanceProbability());
        }
    }


    public RepeatStatus calculateEPCEPSForSourcePLT() throws Exception {
        log.debug("Start CALCULATE_EPC_EPS_FOR_SOURCE_PLT");
        Date startDate = new Date();
        extractEPRPMap();
        for (TransformationBundleNonRMS bundle : transformationPackage.getBundles()) {
            // start new step (import progress) : step 3 CALCULATE_EPC_EPS_FOR_SOURCE_PLT for this analysis in loop of many analysis :
            // only valid sourceResults after step 1 are converted to bundles
//            ProjectImportAssetLog projectImportAssetLogA = new ProjectImportAssetLog();
//            mongoDBSequence.nextSequenceId(projectImportAssetLogA);
//            projectImportAssetLogA.setProjectId(getProjectId());
//            projectImportAssetLogA.setStepId(3);
//            projectImportAssetLogA.setStepName(StepNonRMS.getStepNameFromStepIdForAnalysis(projectImportAssetLogA.getStepId()));
//            projectImportAssetLogA.setStartDate(startDate);
//            projectImportAssetLogA.setProjectImportLogId(bundle.getProjectImportLogAnalysisId());
            // --------------------------------------------------------------

            // TODO logic here
            List<PLTBundleNonRMS> pltBundleNonRMSList = bundle.getPltBundles();

            for (PLTBundleNonRMS pltBundleNonRMS : pltBundleNonRMSList) {
                PltHeaderEntity pltHeader = pltBundleNonRMS.getHeader();
                if (pltHeader.getId() == null) {
                    daoService.getMongoDBSequence().nextSequenceId(pltHeader);
                }

                // create RRLossTable
                LossDataHeaderEntity rrLossTable = new LossDataHeaderEntity();
//                mongoDBSequence.nextSequenceId(rrLossTable);
//                rrLossTable.setProjectId(getProjectId());
//                rrLossTable.setRrRepresentationDatasetId(transformationPackage.getRrRepresentationDatasetId());
                rrLossTable.setModelAnalysisId(bundle.getRrAnalysis().getRrAnalysisId());
                rrLossTable.setLossTableType("PLT");
                rrLossTable.setFileDataFormat("Treaty");
                rrLossTable.setOriginalTarget(RRLossTableType.SOURCE.toString());
                if (pltHeader.getPltLossDataFile() != null) {
                    rrLossTable.setLossDataFile(new LossDataFile(pltHeader.getPltLossDataFile().getFileName(), pltHeader.getPltLossDataFile().getPath()));
                }


                RRFinancialPerspective rrFinancialPerspective = null;
                if (bundle.getRrAnalysis().getFinancialPerspective() != null) {
                    rrFinancialPerspective = new RRFinancialPerspective(bundle.getRrAnalysis().getSourceModellingVendor(),
                            bundle.getRrAnalysis().getSourceModellingSystem(),
                            bundle.getRrAnalysis().getSourceModellingSystemVersion() != null ? bundle.getRrAnalysis().getSourceModellingSystemVersion().toString() : null,
                            bundle.getRrAnalysis().getFinancialPerspective());
                }

                rrLossTable.setFinancialPerspective(rrFinancialPerspective);
                rrLossTable.setCurrency(bundle.getRrAnalysis().getSourceCurrency()); //  source currency
                rrLossTable.setRegionPeril(bundle.getRrAnalysis().getRegionPeril());
                rrLossTable.setExchangeRate(1.0);
                rrLossTable.setProportion(bundle.getRrAnalysis().getProportion());
                rrLossTable.setUnitsMultiplier(bundle.getRrAnalysis().getUnitMultiplier());
                rrLossTable.setUserSelectedGrain(bundle.getRrAnalysis().getGrain());

                RRSummaryStatistic summaryStatistic = new RRSummaryStatistic(); // PLT
                Map<StatisticMetric, List<RREPCurve>> metricToEPCurve = new HashMap<>();

                log.debug("runCalculationForLosses for ptlHearer id {} - {} simulation periods", pltHeader.getPltHeaderId(), pltHeader.getPltSimulationPeriods());
                runCalculationForLosses(pltHeader.getPltSimulationPeriods(), bundle.getPltLossDataList(), epRPMap, rpEPMap, summaryStatistic, metricToEPCurve);

                List<SummaryStatisticHeaderEntity> rrStatisticHeaderList = new ArrayList<>();
                List<String> rrStatisticHeaderIdsList = new ArrayList<>();

                for (Map.Entry<StatisticMetric, List<RREPCurve>> entry : metricToEPCurve.entrySet()) {
                    SummaryStatisticHeaderEntity rrStatisticHeader = new SummaryStatisticHeaderEntity();
                    daoService.getMongoDBSequence().nextSequenceId(rrStatisticHeader);
                    rrStatisticHeader.setProjectId(getProjectId());
                    rrStatisticHeader.setLossDataType(RRStatisticHeader.STAT_DATA_TYPE_PLT);
                    rrStatisticHeader.setLossTableId(rrLossTable.getId());
                    rrStatisticHeader.getStatisticData().setSummaryStatistic(summaryStatistic);
                    rrStatisticHeader.getStatisticData().setStatisticMetric(entry.getKey());
                    rrStatisticHeader.getStatisticData().setEpCurves(entry.getValue());
                    rrStatisticHeader.setFinancialPerspective(rrLossTable.getFinancialPerspective());
                    rrStatisticHeaderList.add(rrStatisticHeader);
                    rrStatisticHeaderIdsList.add(rrStatisticHeader.getId());
                }

                pltHeader.setPltStatisticList(rrStatisticHeaderList);

                List<StatFile> statFiles = new ArrayList<>();

                StatFile statFile = new StatFile();
                statFile.setFinancialPerspective(rrFinancialPerspective);
                statFile.setStatisticHeaders(rrStatisticHeaderIdsList);
                statFiles.add(statFile);
                rrLossTable.setStatFiles(statFiles);
                lossDataHeaderEntityRepository.save(rrLossTable);
                summaryStatisticHeaderRepository.save(rrStatisticHeaderList);

                pltHeader.setRrLossTableId(rrLossTable.getLossDataHeaderId());
            }

            // finis step 3 CALCULATE_EPC_EPS_FOR_SOURCE_PLT for one analysis in loop for of many analysis
//            Date endDate = new Date();
//            projectImportAssetLogA.setEndDate(endDate);
//            projectImportAssetLogRepository.save(projectImportAssetLogA);
            log.info("Finish import progress STEP 3 : CALCULATE_EPC_EPS_FOR_SOURCE_PLT for RRAnalysis : {}", bundle.getRrAnalysis().getRrAnalysisId());
        }


        return RepeatStatus.FINISHED;
    }


    public void runCalculationForLosses(int nYears, List<PLTLossData> sortedLossData, Map<BigDecimal, Integer> epRPMap, Map<Integer, Double> rpEPMap, SummaryStatisticHeaderEntity pltSummaryStatistic, Map<StatisticMetric, List<RREPCurve>> metricToEPCurve) {
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

    public TransformationPackageNonRMS getTransformationPackage() {
        return transformationPackage;
    }

    public void setTransformationPackage(TransformationPackageNonRMS transformationPackage) {
        this.transformationPackage = transformationPackage;
    }


}
