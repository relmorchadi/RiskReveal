package com.scor.rr.service.batch;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scor.rr.domain.AnalysisEpCurves;
import com.scor.rr.domain.AnalysisSummaryStats;
import com.scor.rr.domain.RmsExchangeRate;
import com.scor.rr.domain.dto.BinFile;
import com.scor.rr.domain.enums.FinancialPerspectiveCodeEnum;
import com.scor.rr.domain.enums.StatisticMetric;
import com.scor.rr.domain.enums.StatisticsType;
import com.scor.rr.domain.model.EPCurveHeaderEntity;
import com.scor.rr.domain.model.LossDataHeaderEntity;
import com.scor.rr.domain.model.SummaryStatisticHeaderEntity;
import com.scor.rr.domain.riskLink.RLAnalysis;
import com.scor.rr.domain.riskLink.RLModelDataSource;
import com.scor.rr.domain.riskLink.RlSourceEpHeader;
import com.scor.rr.domain.ModelAnalysisEntity;
import com.scor.rr.repository.*;
import com.scor.rr.service.RmsService;
import com.scor.rr.service.batch.writer.EpCurveWriter;
import com.scor.rr.service.batch.writer.EpSummaryStatWriter;
import com.scor.rr.service.state.TransformationBundle;
import com.scor.rr.service.state.TransformationPackage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.*;

import static com.scor.rr.util.PathUtils.makeEpCurveFileName;
import static com.scor.rr.util.PathUtils.makeEpSummaryStatFileName;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

@Service
@StepScope
@Slf4j
public class EpCurveExtractor {


    @Autowired
    private RlModelDataSourceRepository dataSourceRepository;

    @Autowired
    private RmsService rmsService;

    @Autowired
    private DefaultReturnPeriodRepository defaultReturnPeriodRepository;

    @Autowired
    private EPCurveHeaderRepository epCurveHeaderRepository;

    @Autowired
    private SummaryStatisticHeaderRepository summaryStatisticHeaderRepository;

    @Autowired
    private LossDataHeaderRepository lossDataHeaderRepository;

    @Autowired
    private EpCurveWriter epCurveWriter;

    @Autowired
    private EpSummaryStatWriter epSummaryStatWriter;

    @Autowired
    private TransformationPackage transformationPackage;

    private String fpTYCode = "TY";

    private Gson gson = new Gson();

    /**
     * Extract EP Curve Tasklet main entry
     *
     * @return RepeatStatus
     */
    public RepeatStatus extractEpCurve() {
        // Given some transformation bundles

        List<TransformationBundle> transformationBundle = transformationPackage.getTransformationBundles();

        transformationBundle.forEach(bundle -> {
            RLAnalysis riskLinkAnalysis = bundle.getRlAnalysis();
            String instanceId = bundle.getInstanceId();
            ModelAnalysisEntity modelAnalysisEntity = bundle.getModelAnalysisEntity();
            List<RlSourceEpHeader> epHeaders = riskLinkAnalysis.getRlSourceEpHeaders();
            String selectedFp = bundle.getFinancialPerspective();

            Long analysisId = riskLinkAnalysis.getRlAnalysisId();
            Long rdmId = riskLinkAnalysis.getRdmId();
            String rdmName = riskLinkAnalysis.getRdmName();

            LossDataHeaderEntity lossDataHeaderEntity = bundle.getSourceRRLT();

            List<String> sourceFPs = epHeaders.stream().map(item -> item.getFinancialPerpective()).collect(toList());
            List<String> filteredFPs = sourceFPs.stream().filter(fp -> !StringUtils.equalsIgnoreCase(fp, FinancialPerspectiveCodeEnum.TY.getCode())).collect(toList());

            if (!StringUtils.equalsIgnoreCase(selectedFp, FinancialPerspectiveCodeEnum.TY.getCode()))
                filteredFPs.add(selectedFp);

            EpCurveExtractResult epCurveExtractResult = this.mapFinancialPerspectiveToSummaryStats(filteredFPs, riskLinkAnalysis, instanceId, rdmId, rdmName, analysisId);

            lossDataHeaderRepository.save(lossDataHeaderEntity);
            Long lossDataHeaderId = lossDataHeaderEntity.getLossDataHeaderId();
            List<EPCurveHeaderEntity> epCurves = this.generateEpCurveHeaders(filteredFPs, epCurveExtractResult, modelAnalysisEntity, lossDataHeaderId);
            List<SummaryStatisticHeaderEntity> summaryStats = this.generateSummaryStatsHeader(filteredFPs, epCurveExtractResult, lossDataHeaderId);

            epCurveHeaderRepository.saveAll(epCurves);
            summaryStatisticHeaderRepository.saveAll(summaryStats);

            // Push results into the Bundle
            bundle.setEpCurves(epCurves);
            bundle.setSummaryStatisticHeaderEntities(summaryStats);
        });


        return RepeatStatus.FINISHED;
    }

    public RepeatStatus extractConformedEpCurves() {
        log.debug("Starting RMSEPCurveExtractor.runConformedExtraction");

        for (TransformationBundle bundle : transformationPackage.getTransformationBundles()) {


            LossDataHeaderEntity sourceRRLT = bundle.getSourceRRLT();
            LossDataHeaderEntity conformedRRLT = bundle.getConformedRRLT();


            double proportion = bundle.getSourceResult().getProportion() == null ? 1 : bundle.getSourceResult().getProportion().doubleValue() / 100;
            double multiplier = bundle.getSourceResult().getUnitMultiplier() == null ? 1 : bundle.getSourceResult().getUnitMultiplier().doubleValue();
            log.info("Conforming EP curves and sum stats for conformedRRLT {}, proportion {}, multiplier {}", bundle.getConformedRRLT().getLossDataHeaderId(), proportion, multiplier);

            double exchangeRate = 1.0d;
            if (!conformedRRLT.getCurrency().equals(sourceRRLT.getCurrency())) {
                double sourceExchangeRate = 1.0d;
                double targetExchangeRate = 1.0d;
                for (RmsExchangeRate rmsExchangeRate : bundle.getRmsExchangeRatesOfRRLT()) {
                    if (rmsExchangeRate.getCcy().equals(sourceRRLT.getCurrency()))
                        sourceExchangeRate = rmsExchangeRate.getExchangeRate();
                    else if (rmsExchangeRate.getCcy().equals(conformedRRLT.getCurrency()))
                        targetExchangeRate = rmsExchangeRate.getExchangeRate();
                    else
                        log.debug("Something wrong: ccy {} found for source ELT currency {} conformed ELT currency {}", rmsExchangeRate.getCcy(), sourceRRLT.getCurrency(), conformedRRLT.getCurrency());
                }

                exchangeRate = targetExchangeRate / sourceExchangeRate;
            }
            log.debug("source ELT currency {} conformed ELT currency {} exchange rate {}", sourceRRLT.getCurrency(), conformedRRLT.getCurrency(), exchangeRate);

            List<SummaryStatisticHeaderEntity> conformedSummaryStatHeaders = new ArrayList<>();

            for (SummaryStatisticHeaderEntity statisticHeader : bundle.getSummaryStatisticHeaderEntities()) {

                SummaryStatisticHeaderEntity confSumStat = conformSummaryStatistic(statisticHeader, proportion, multiplier, exchangeRate);
                confSumStat.setLossDataId(bundle.getConformedRRLT().getLossDataHeaderId());

                AnalysisSummaryStats analysisSummaryStats = new AnalysisSummaryStats();
                analysisSummaryStats.setCov(statisticHeader.getCov());
                analysisSummaryStats.setPurePremium(statisticHeader.getPurePremium());
                analysisSummaryStats.setStdDev(statisticHeader.getStandardDeviation());
                analysisSummaryStats.setFpCode(statisticHeader.getFinancialPerspective());
                //TODO : Where to get epTypeCode

                String fileName = makeEpSummaryStatFileName(statisticHeader.getFinancialPerspective(), new Date(), ".bin");
                BinFile fileSummaryStat = epSummaryStatWriter.writePLTSummaryStatistics(analysisSummaryStats, fileName);

                confSumStat.setEPSFilePath(fileSummaryStat.getPath());
                confSumStat.setEPSFileName(fileSummaryStat.getFileName());

                conformedSummaryStatHeaders.add(confSumStat);
            }

            List<EPCurveHeaderEntity> conformedEpCurvesHeaders = new ArrayList<>();
            List<AnalysisEpCurves> confEPCurvesList = new ArrayList<>();

            for (EPCurveHeaderEntity epCurveHeaderEntity : bundle.getEpCurves()) {
                Type listType = new TypeToken<ArrayList<AnalysisEpCurves>>() {
                }.getType();
                List<AnalysisEpCurves> confEPCurves =
                        conformELTEPCurves(
                                gson.fromJson(epCurveHeaderEntity.getEPCurves(), listType),
                                proportion,
                                multiplier,
                                exchangeRate);
                confEPCurvesList.addAll(confEPCurves);

                EPCurveHeaderEntity conformedEPCurvesHeader = EPCurveHeaderEntity.builder()
                        .entity(epCurveHeaderEntity.getEntity())
                        .lossDataType(epCurveHeaderEntity.getLossDataType())
                        .statisticMetric(epCurveHeaderEntity.getStatisticMetric())
                        .ePCurves(gson.toJson(confEPCurves))
                        .lossDataId(bundle.getConformedRRLT().getLossDataHeaderId())
                        .financialPerspective(epCurveHeaderEntity.getFinancialPerspective())
                        .build();
                conformedEpCurvesHeaders.add(conformedEPCurvesHeader);
            }

            String makeEpCurveFileName = makeEpCurveFileName(bundle.getFinancialPerspective(), bundle.getModelAnalysisEntity().getCreationDate(), ".bin");
            BinFile file = epCurveWriter.writeELTEPCurves(confEPCurvesList, makeEpCurveFileName);

            conformedEpCurvesHeaders.forEach(epCurveHeaderEntity -> {
                epCurveHeaderEntity.setEPCFilePath(file.getPath());
                epCurveHeaderEntity.setEPCFileName(file.getFileName());
            });


            lossDataHeaderRepository.save(conformedRRLT);
            summaryStatisticHeaderRepository.saveAll(conformedSummaryStatHeaders);
            epCurveHeaderRepository.saveAll(conformedEpCurvesHeaders);


            log.info("Finish import progress STEP 7 : EXTRACT_CONFORMED_EPCURVE_STATS for analysis: {}", bundle.getSourceResult().getRlSourceResultId());
        }
        log.debug("RMSEPCurveExtractor.runConformedExtraction completed");
        return RepeatStatus.FINISHED;
    }

    /**
     * @param fPs
     * @param riskLinkAnalysis
     * @param defaultInstanceId
     * @param rdmId
     * @param rdmName
     * @param analysisId
     * @return
     */
    private EpCurveExtractResult mapFinancialPerspectiveToSummaryStats(List<String> fPs, RLAnalysis riskLinkAnalysis, String defaultInstanceId, Long rdmId, String rdmName, Long analysisId) {
        EpCurveExtractResult result = new EpCurveExtractResult();
        fPs.forEach(fp -> {
            Map<StatisticMetric, List<AnalysisEpCurves>> metricToEPCurve = new HashMap<>();
            Long datasourceId = riskLinkAnalysis.getRlModelDataSourceId();
            RLModelDataSource datasource = dataSourceRepository.findById(datasourceId).orElseThrow(() -> new RuntimeException("No available datasource with ID " + datasourceId));
            String instanceId = ofNullable(datasource).map(ds -> ds.getInstanceId()).orElse(defaultInstanceId);

            // @TODO : Get the treaty label regarding the FP Object
            rmsService.getAnalysisEpCurves(rdmId, rdmName, analysisId, fp, null)
                    .forEach(epCurve -> {
                        StatisticMetric metric = StatisticMetric.getFrom(epCurve.getEpTypeCode());
                        if (metricToEPCurve.containsKey(metric) && metricToEPCurve.get(metric) != null) {
                            metricToEPCurve.get(metric).add(epCurve);
                        } else{
                            metricToEPCurve.put(metric, new ArrayList<>(Collections.singleton(epCurve)));

                        }
                    });

            result.fpToELTEPCurves.put(fp, metricToEPCurve);

            // @TODO : Get the treaty label regarding the FP Object
            AnalysisSummaryStats summaryStats =
                    ofNullable(rmsService.getAnalysisSummaryStats(rdmId, rdmName, analysisId, fp, null))
                            .map(l -> l.get(0)).orElse(new AnalysisSummaryStats());
            result.fpToELTSumStat.put(fp, summaryStats);
        });
        return result;
    }

    private List<EPCurveHeaderEntity> generateEpCurveHeaders(List<String> fPs, EpCurveExtractResult epCurveExtractResult, ModelAnalysisEntity modelAnalysisEntity, Long lossDataHeaderId) {
        return fPs.stream().map(fp -> {
            List<EPCurveHeaderEntity> epCurves = new ArrayList<>();
            Map<StatisticMetric, List<AnalysisEpCurves>> metricToEPCurves = epCurveExtractResult.fpToELTEPCurves.get(fp);
            metricToEPCurves.forEach((statisticMetric, analysisEpCurves) -> {
                String makeEpCurveFileName = makeEpCurveFileName(fp, modelAnalysisEntity.getCreationDate(), ".bin");
                BinFile file = epCurveWriter.writeELTEPCurves(metricToEPCurves.get(statisticMetric), makeEpCurveFileName);
                epCurves.add(
                        EPCurveHeaderEntity.builder()
                                .ePCurveHeaderId(null)
                                .entity(1)
                                .financialPerspective(fp)
                                .statisticMetric(statisticMetric)
                                .lossDataType(StatisticsType.ELT.getCode())
                                .ePCurves(gson.toJson(analysisEpCurves)) // to be implemented
                                .ePCFileName(makeEpCurveFileName)
                                .ePCFilePath(file.getPath())
                                .lossDataId(lossDataHeaderId)
                                .build()
                );
            });

            return epCurves;
        }).flatMap(List::stream).collect(toList());
    }

    private List<SummaryStatisticHeaderEntity> generateSummaryStatsHeader(List<String> fPs, EpCurveExtractResult epCurveExtractResult, Long lossDataHeaderId) {
        return fPs.stream().map(fp -> {
            AnalysisSummaryStats summaryStats = epCurveExtractResult.fpToELTSumStat.get(fp);
            //@TODO Review
            String fileName = makeEpSummaryStatFileName(fp, new Date(), ".bin");
            BinFile file = epSummaryStatWriter.writePLTSummaryStatistics(summaryStats, fileName);
            return new SummaryStatisticHeaderEntity(1, fp, summaryStats.getCov(), summaryStats.getStdDev(),
                    summaryStats.getPurePremium(), StatisticsType.ELT.getCode(), lossDataHeaderId, fileName, file.getPath());
        }).collect(toList());
    }

    private static class EpCurveExtractResult {
        Map<String, Map<StatisticMetric, List<AnalysisEpCurves>>> fpToELTEPCurves;
        Map<String, AnalysisSummaryStats> fpToELTSumStat;

        public EpCurveExtractResult() {
            fpToELTEPCurves = new HashMap<>();
            fpToELTSumStat = new HashMap<>();
        }
    }

    private SummaryStatisticHeaderEntity conformSummaryStatistic(SummaryStatisticHeaderEntity input, double proportion, double multiplier, double exchangeRate) {
        SummaryStatisticHeaderEntity output = new SummaryStatisticHeaderEntity(input);
        output.setCov(input.getCov());
        output.setPurePremium(input.getPurePremium() * proportion * multiplier * exchangeRate);
        output.setStandardDeviation(input.getStandardDeviation() * proportion * multiplier * exchangeRate);
        return output;
    }

    private List<AnalysisEpCurves> conformELTEPCurves(List<AnalysisEpCurves> inputs, double proportion, double multiplier, double exchangeRate) {
        List<AnalysisEpCurves> outputs = new ArrayList<>();
        for (AnalysisEpCurves input : inputs) {
            AnalysisEpCurves output = new AnalysisEpCurves();

            output.setExeceedanceProb(input.getExeceedanceProb());
            output.setLoss(input.getLoss() * proportion * multiplier * exchangeRate);

            output.setAnalysisId(input.getAnalysisId());
            output.setEpTypeCode(input.getEpTypeCode());
            output.setFinPerspCode(input.getFinPerspCode());

            outputs.add(output);
        }
        return outputs;
    }

}
