package com.scor.rr.service.batch;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scor.rr.configuration.file.BinFile;
import com.scor.rr.domain.*;
import com.scor.rr.domain.enums.*;
import com.scor.rr.domain.riskLink.RLAnalysis;
import com.scor.rr.domain.riskLink.RLModelDataSource;
import com.scor.rr.domain.riskLink.RLSourceEpHeader;
import com.scor.rr.repository.EPCurveHeaderEntityRepository;
import com.scor.rr.repository.LossDataHeaderEntityRepository;
import com.scor.rr.repository.RLModelDataSourceRepository;
import com.scor.rr.repository.SummaryStatisticHeaderRepository;
import com.scor.rr.service.RmsService;
import com.scor.rr.service.batch.writer.AbstractWriter;
import com.scor.rr.service.batch.writer.EpCurveWriter;
import com.scor.rr.service.batch.writer.EpSummaryStatWriter;
import com.scor.rr.service.state.TransformationBundle;
import com.scor.rr.service.state.TransformationPackage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.*;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

@Service
@StepScope
@Slf4j
public class EpCurveExtractor extends AbstractWriter {


    @Autowired
    private RLModelDataSourceRepository dataSourceRepository;

    @Autowired
    private RmsService rmsService;

    @Autowired
    private EPCurveHeaderEntityRepository epCurveHeaderEntityRepository;

    @Autowired
    private SummaryStatisticHeaderRepository summaryStatisticHeaderRepository;

    @Autowired
    private LossDataHeaderEntityRepository lossDataHeaderEntityRepository;

    @Autowired
    private EpCurveWriter epCurveWriter;

    @Autowired
    private EpSummaryStatWriter epSummaryStatWriter;

    @Autowired
    private TransformationPackage transformationPackage;

    @Value("#{jobParameters['marketChannel']}")
    private String marketChannel;

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
            ModelAnalysisEntity modelAnalysisEntity = bundle.getModelAnalysis();

            List<RLSourceEpHeader> epHeaders = riskLinkAnalysis.getRlSourceEpHeaders();
            String selectedFp = bundle.getFinancialPerspective();

            Long analysisId = riskLinkAnalysis.getRlAnalysisId();
            Long rdmId = riskLinkAnalysis.getRdmId();
            String rdmName = riskLinkAnalysis.getRdmName();

            LossDataHeaderEntity lossDataHeaderEntity = bundle.getSourceRRLT();

            List<String> sourceFPs = epHeaders.stream().map(RLSourceEpHeader::getFinancialPerspective).collect(toList());
            List<String> filteredFPs = sourceFPs.stream().filter(fp -> !StringUtils.equalsIgnoreCase(fp, FinancialPerspectiveCodeEnum.TY.getCode())).collect(toList());

            if (!StringUtils.equalsIgnoreCase(selectedFp, FinancialPerspectiveCodeEnum.TY.getCode()))
                filteredFPs.add(selectedFp);

            EpCurveExtractResult epCurveExtractResult = this.mapFinancialPerspectiveToSummaryStats(filteredFPs, riskLinkAnalysis, instanceId, rdmId, rdmName, analysisId);

            lossDataHeaderEntity = lossDataHeaderEntityRepository.save(lossDataHeaderEntity);
            List<EPCurveHeaderEntity> epCurves = this.generateEpCurveHeaders(filteredFPs, epCurveExtractResult, modelAnalysisEntity, lossDataHeaderEntity);
            List<SummaryStatisticHeaderEntity> summaryStats = this.generateSummaryStatsHeader(filteredFPs, modelAnalysisEntity, epCurveExtractResult, lossDataHeaderEntity);

            epCurveHeaderEntityRepository.saveAll(epCurves);
            summaryStatisticHeaderRepository.saveAll(summaryStats);

            // Push results into the Bundle
            bundle.setEpCurves(epCurves);
            bundle.setSummaryStatisticHeaderEntities(summaryStats);
        });


        return RepeatStatus.FINISHED;
    }

    public RepeatStatus extractConformedEpCurves() {


        if (marketChannel.equalsIgnoreCase("Treaty")) {
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

                bundle.setConformedRRLT(lossDataHeaderEntityRepository.save(conformedRRLT));
                conformedRRLT = bundle.getConformedRRLT();

                for (SummaryStatisticHeaderEntity statisticHeader : bundle.getSummaryStatisticHeaderEntities()) {

                    SummaryStatisticHeaderEntity confSumStat = conformSummaryStatistic(statisticHeader, proportion, multiplier, exchangeRate);
                    confSumStat.setLossDataId(bundle.getConformedRRLT().getLossDataHeaderId());

                    AnalysisSummaryStats analysisSummaryStats = new AnalysisSummaryStats();
                    analysisSummaryStats.setCov(statisticHeader.getCov());
                    analysisSummaryStats.setPurePremium(statisticHeader.getPurePremium());
                    analysisSummaryStats.setStdDev(statisticHeader.getStandardDeviation());
                    analysisSummaryStats.setFpCode(statisticHeader.getFinancialPerspective());
                    //TODO : Where to get epTypeCode

                    String fileName = makeELTSummaryStatFilename(
                            bundle.getModelAnalysis().getCreationDate(),
                            bundle.getModelAnalysis().getRegionPeril(),
                            bundle.getFinancialPerspective(),
                            conformedRRLT.getCurrency(),
                            conformedRRLT.getOriginalTarget().equals(RRLossTableType.SOURCE.getCode()) ? XLTOT.ORIGINAL : XLTOT.TARGET,
                            conformedRRLT.getLossDataHeaderId(),
                            bundle.getModelAnalysis().getDivision(),
                            ".bin");
                    BinFile fileSummaryStat = epSummaryStatWriter.writeELTSummaryStatistics(analysisSummaryStats, fileName, bundle.getModelAnalysis().getDivision());

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

                String makeEpCurveFileName = makeELTEPCurveFilename(
                        bundle.getModelAnalysis().getCreationDate(),
                        bundle.getModelAnalysis().getRegionPeril(),
                        bundle.getFinancialPerspective(),
                        conformedRRLT.getCurrency(),
                        conformedRRLT.getOriginalTarget().equals(RRLossTableType.SOURCE.getCode()) ? XLTOT.ORIGINAL : XLTOT.TARGET,
                        conformedRRLT.getLossDataHeaderId(),
                        bundle.getModelAnalysis().getDivision(),
                        ".bin");
                BinFile file = epCurveWriter.writeELTEPCurves(confEPCurvesList, makeEpCurveFileName, bundle.getModelAnalysis().getDivision());

                conformedEpCurvesHeaders.forEach(epCurveHeaderEntity -> {
                    epCurveHeaderEntity.setEPCFilePath(file.getPath());
                    epCurveHeaderEntity.setEPCFileName(file.getFileName());
                });


                lossDataHeaderEntityRepository.save(conformedRRLT);
                summaryStatisticHeaderRepository.saveAll(conformedSummaryStatHeaders);
                epCurveHeaderEntityRepository.saveAll(conformedEpCurvesHeaders);


                log.info("Finish import progress STEP 7 : EXTRACT_CONFORMED_EPCURVE_STATS for analysis: {}", bundle.getSourceResult().getRlImportSelectionId());
            }
            log.debug("RMSEPCurveExtractor.runConformedExtraction completed");
        }
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
            Long rlId = riskLinkAnalysis.getRlId();
            RLModelDataSource datasource = dataSourceRepository.findById(datasourceId).orElseThrow(() -> new RuntimeException("No available datasource with ID " + datasourceId));
            String instanceId = ofNullable(datasource).map(ds -> ds.getInstanceId()).orElse(defaultInstanceId);

            // @TODO : Get the treaty label regarding the FP Object
            rmsService.getAnalysisEpCurves(instanceId, rdmId, rdmName, rlId, fp, null)
                    .forEach(epCurve -> {
                        StatisticMetric metric = StatisticMetric.getFrom(epCurve.getEpTypeCode());
                        if (metricToEPCurve.containsKey(metric) && metricToEPCurve.get(metric) != null) {
                            metricToEPCurve.get(metric).add(epCurve);
                        } else {
                            metricToEPCurve.put(metric, new ArrayList<>(Collections.singleton(epCurve)));

                        }
                    });

            result.fpToELTEPCurves.put(fp, metricToEPCurve);

            // @TODO : Get the treaty label regarding the FP Object
            AnalysisSummaryStats summaryStats =
                    ofNullable(rmsService.getAnalysisSummaryStats(instanceId, rdmId, rdmName, rlId, fp, null))
                            .map(l -> l.isEmpty() ? null : l ).map(l -> l.get(0)).orElse(new AnalysisSummaryStats());
            result.fpToELTSumStat.put(fp, summaryStats);
        });
        return result;
    }

    private List<EPCurveHeaderEntity> generateEpCurveHeaders(List<String> fPs, EpCurveExtractResult epCurveExtractResult, ModelAnalysisEntity modelAnalysis, LossDataHeaderEntity lossDataHeader) {
        return fPs.stream().map(fp -> {
            List<EPCurveHeaderEntity> epCurves = new ArrayList<>();
            Map<StatisticMetric, List<AnalysisEpCurves>> metricToEPCurves = epCurveExtractResult.fpToELTEPCurves.get(fp);
            metricToEPCurves.forEach((statisticMetric, analysisEpCurves) -> {
                String makeEpCurveFileName = makeELTEPCurveFilename(
                        modelAnalysis.getCreationDate(),
                        modelAnalysis.getRegionPeril(),
                        fp,
                        lossDataHeader.getCurrency(),
                        lossDataHeader.getOriginalTarget().equals(RRLossTableType.SOURCE.getCode()) ? XLTOT.ORIGINAL : XLTOT.TARGET,
                        lossDataHeader.getLossDataHeaderId(),
                        modelAnalysis.getDivision(),
                        ".bin");
                BinFile file = epCurveWriter.writeELTEPCurves(metricToEPCurves.get(statisticMetric), makeEpCurveFileName, modelAnalysis.getDivision());
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
                                .lossDataId(lossDataHeader.getLossDataHeaderId())
                                .build()
                );
            });

            return epCurves;
        }).flatMap(List::stream).collect(toList());
    }

    private List<SummaryStatisticHeaderEntity> generateSummaryStatsHeader(List<String> fPs, ModelAnalysisEntity modelAnalysis, EpCurveExtractResult epCurveExtractResult, LossDataHeaderEntity lossDataHeader) {
        return fPs.stream().map(fp -> {
            AnalysisSummaryStats summaryStats = epCurveExtractResult.fpToELTSumStat.get(fp);
            //@TODO Review
            String fileName = makeELTSummaryStatFilename(
                    modelAnalysis.getCreationDate(),
                    modelAnalysis.getRegionPeril(),
                    fp,
                    lossDataHeader.getCurrency(),
                    lossDataHeader.getOriginalTarget().equals(RRLossTableType.SOURCE.getCode()) ? XLTOT.ORIGINAL : XLTOT.TARGET,
                    lossDataHeader.getLossDataHeaderId(),
                    modelAnalysis.getDivision(),
                    ".bin");
            BinFile file = epSummaryStatWriter.writeELTSummaryStatistics(summaryStats, fileName, modelAnalysis.getDivision());
            return new SummaryStatisticHeaderEntity(1L, fp, summaryStats.getCov(), summaryStats.getStdDev(),
                    summaryStats.getPurePremium(), StatisticsType.ELT.getCode(), lossDataHeader.getLossDataHeaderId(), fileName, file.getPath());
        }).collect(toList());
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

    private static class EpCurveExtractResult {
        Map<String, Map<StatisticMetric, List<AnalysisEpCurves>>> fpToELTEPCurves;
        Map<String, AnalysisSummaryStats> fpToELTSumStat;

        public EpCurveExtractResult() {
            fpToELTEPCurves = new HashMap<>();
            fpToELTSumStat = new HashMap<>();
        }
    }

}
