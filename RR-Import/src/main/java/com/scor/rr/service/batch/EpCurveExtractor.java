package com.scor.rr.service.batch;

import com.scor.rr.domain.AnalysisEpCurves;
import com.scor.rr.domain.AnalysisSummaryStats;
import com.scor.rr.domain.RmsExchangeRate;
import com.scor.rr.domain.dto.BinFile;
import com.scor.rr.domain.enums.FinancialPerspectiveCodeEnum;
import com.scor.rr.domain.enums.StatisticMetric;
import com.scor.rr.domain.enums.StatisticsType;
import com.scor.rr.domain.model.EPCurveHeader;
import com.scor.rr.domain.model.LossDataHeader;
import com.scor.rr.domain.model.SummaryStatisticHeader;
import com.scor.rr.domain.riskLink.RLAnalysis;
import com.scor.rr.domain.riskLink.RlModelDataSource;
import com.scor.rr.domain.riskLink.RlSourceEpHeader;
import com.scor.rr.domain.riskReveal.RRAnalysis;
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

import java.util.*;

import static com.scor.rr.util.PathUtils.makeEpCurveFileName;
import static com.scor.rr.util.PathUtils.makeEpSummaryStatFileName;
import static java.util.Arrays.asList;
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
            Long instanceId = bundle.getInstanceId();
            RRAnalysis rrAnalysis = bundle.getRrAnalysis();
            List<RlSourceEpHeader> epHeaders = riskLinkAnalysis.getRlSourceEpHeaders();
            String selectedFp= bundle.getFinancialPerspective();

            Long analysisId = riskLinkAnalysis.getRlAnalysisId();
            Long rdmId = riskLinkAnalysis.getRdmId();
            String rdmName = riskLinkAnalysis.getRdmName();

            LossDataHeader lossDataHeader = bundle.getSourceRRLT();

            List<String> sourceFPs = epHeaders.stream().map(item -> item.getFinancialPerpective()).collect(toList());
            List<String> filteredFPs = sourceFPs.stream().filter(fp -> !StringUtils.equalsIgnoreCase(fp, FinancialPerspectiveCodeEnum.TY.getCode())).collect(toList());

            if (StringUtils.equalsIgnoreCase(selectedFp, FinancialPerspectiveCodeEnum.TY.getCode()))
                filteredFPs.add(selectedFp);

            EpCurveExtractResult epCurveExtractResult = this.mapFinancialPerspectiveToSummaryStats(filteredFPs, riskLinkAnalysis, instanceId, rdmId, rdmName, analysisId);

            lossDataHeaderRepository.save(lossDataHeader);
            Long lossDataHeaderId = lossDataHeader.getLossDataHeaderId();
            List<EPCurveHeader> epCurves= this.generateEpCurveHeaders(filteredFPs, epCurveExtractResult, rrAnalysis, lossDataHeaderId);
            List<SummaryStatisticHeader> summaryStats= this.generateSummaryStatsHeader(filteredFPs, epCurveExtractResult, lossDataHeaderId);

            epCurveHeaderRepository.saveAll(epCurves);
            summaryStatisticHeaderRepository.saveAll(summaryStats);

            // Push results into the Bundle
            bundle.setEpCurves(epCurves);
            bundle.setSummaryStatisticHeaders(summaryStats);
        });


        return RepeatStatus.FINISHED;
    }

    public RepeatStatus extractConformedEpCurves() {
        log.debug("Starting RMSEPCurveExtractor.runConformedExtraction");

        for (TransformationBundle bundle : transformationPackage.getTransformationBundles()) {


            LossDataHeader sourceRRLT = bundle.getSourceRRLT();
            LossDataHeader conformedRRLT = bundle.getConformedRRLT();


//            List<ELTEPHeader> confELTEPHeaders = new ArrayList<>(); <--> confRrStatisticHeaders
//            confELTHeader.setELTEPHeaders(confELTEPHeaders); <--> confRrStatisticHeaders

            List<SummaryStatisticHeader> confRrStatisticHeaders = new ArrayList<>();
            // 1 conformedRRLT --> List<RRStatisticHeader> confRrStatisticHeaders
            // conformedRRLT.setELTEPHeaders(confRrStatisticHeaders); //TODO ?? DBRef to String, 1 way

            Map<String, Map<StatisticMetric, List<EPCurveHeader>>> fp2Metric2EPCurves = new HashMap<>();
            Map<String, SummaryStatisticHeader> fp2SumStat = new HashMap<>();

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

//            List<RRStatisticHeader> rrStatisticHeaderList = rrStatisticHeaderRepository.findByLossTableId(bundle.getSourceRRLT().getId());


//            for (StatFile statFileSource : bundle.getSourceRRLT().getStatFiles()) {
//                StatFile statFileConformed = new StatFile();
//                for (String rrStatisticHeaderId : statFileSource.getStatisticHeaders()) {
//                    RRStatisticHeader rrStatisticHeader = rrStatisticHeaderRepository.findOne(rrStatisticHeaderId);
//                    //            for (ELTEPHeader eltepHeader : eltHeader.getELTEPHeaders()) {
//                    RRFinancialPerspective fp = rrStatisticHeader.getFinancialPerspective();
////                AnalysisFinancialPerspective fp = bundle.getSourceResult().getFinancialPerspective(); // TODO false
//                    StatisticMetric metric = rrStatisticHeader.getStatisticData().getStatisticMetric();
//                    RRSummaryStatistic confSumStat = conformSummaryStatistic(rrStatisticHeader.getStatisticData().getSummaryStatistic(), proportion, multiplier, exchangeRate); // ELTEPSummaryStatistic
//                    List<RREPCurve> confEPCurves = conformELTEPCurves(rrStatisticHeader.getStatisticData().getEpCurves(), proportion, multiplier, exchangeRate);
//
////                ELTEPHeader confELTEPHeader = new ELTEPHeader();
////                confELTEPHeader.setEltHeader(confELTHeader);
////                confELTEPHeader.setFinancialPerspective(fp);
////                confELTEPHeader.setStatisticMetric(metric);
////                confELTEPHeader.setSummaryStatistic(confSumStat);
////                confELTEPHeader.setEpCurves(confEPCurves);
////
////                mongoDBSequence.nextSequenceId(confELTEPHeader);
////                confELTEPHeaders.add(confELTEPHeader);
//
//                    SummaryStatisticHeader confRrStatisticHeader = new SummaryStatisticHeader();
//                    //confRrStatisticHeader.setProjectId(transformationPackage.getProjectId());
//                    confRrStatisticHeader.setLossTableId(conformedRRLT.getRrLossTableHeaderId());
//                    statFileConformed.getStatisticHeaders().add(confRrStatisticHeader.getId());
//                    confRrStatisticHeader.setLossDataType(RRStatisticHeader.STAT_DATA_TYPE_ELT);
//                    confRrStatisticHeader.setFinancialPerspective(fp);
//                    statFileConformed.setFinancialPerspective(fp);
//                    confRrStatisticHeader.getStatisticData().setStatisticMetric(metric);
//                    confRrStatisticHeader.getStatisticData().setSummaryStatistic(confSumStat);
//                    confRrStatisticHeader.getStatisticData().setEpCurves(confEPCurves);
//
//                    confRrStatisticHeaders.add(confRrStatisticHeader);
//
//                    // build map for writing purpose
//                    if (fp2Metric2EPCurves.get(fp) == null) {
//                        Map<StatisticMetric, List<RREPCurve>> metric2EPCurves = new HashMap<>();
//                        metric2EPCurves.put(metric, confEPCurves);
//                        fp2Metric2EPCurves.put(new AnalysisFinancialPerspective(fp), metric2EPCurves);
//                    } else {
//                        fp2Metric2EPCurves.get(fp).put(metric, confEPCurves);
//                    }
//                    if (fp2SumStat.get(fp) == null) {
//                        fp2SumStat.put(new AnalysisFinancialPerspective(fp), confSumStat);
//                    }
//                }
//                conformedRRLT.getStatFiles().add(statFileConformed);
//            }
//
//
//            log.debug("conformed ELTHeader id {} currency {}, ", conformedRRLT.getId(), conformedRRLT.getCurrency());
//
//            for (AnalysisFinancialPerspective fp : fp2SumStat.keySet()) {
//                log.info("Writing conformed EP curves and sum stats for fp {}", fp.getFullCode());
//
//                for (StatFile statFileConformed : conformedRRLT.getStatFiles()) {
//                    if (statFileConformed.getFinancialPerspective().getCode().equals(fp.getCode())) {
//                        // String epcFname = makeELTEPCurveFilename(confELTHeader.getCreatedDate(), eltHeader.getRegionPeril().getRegionPerilCode(), fp.getFullCode(), confELTHeader.getCurrency(), XLTOT.TARGET, confELTHeader.getId(), getFileExtension());
//                        String epcFname = makeELTEPCurveFilename(bundle.getRrAnalysis().getCreationDate(), bundle.getRrAnalysis().getRegionPeril(), fp.getFullCode(), conformedRRLT.getCurrency(), XLTOT.TARGET, conformedRRLT.getId(), getFileExtension());
//                        BinFile binFileEPC = epCurveWriter.writeELTEPCurves(fp2Metric2EPCurves.get(fp), epcFname);
//                        statFileConformed.setEpcFileName(binFileEPC.getFileName());
//                        statFileConformed.setEpcFilePath(binFileEPC.getPath());
//
//                        // String epsFname = makeELTSummaryStatFilename(confELTHeader.getCreatedDate(), eltHeader.getRegionPeril().getRegionPerilCode(), fp.getFullCode(), confELTHeader.getCurrency(), XLTOT.TARGET, confELTHeader.getId(), getFileExtension());
//                        String epsFname = makeELTSummaryStatFilename(bundle.getRrAnalysis().getCreationDate(), bundle.getRrAnalysis().getRegionPeril(), fp.getFullCode(), conformedRRLT.getCurrency(), XLTOT.TARGET, conformedRRLT.getId(), getFileExtension());
//                        BinFile binFileEPS = epsWriter.writePLTSummaryStatistics(fp2SumStat.get(fp), epsFname);
//                        statFileConformed.setEpsFileName(binFileEPS.getFileName());
//                        statFileConformed.setEpsFilePath(binFileEPS.getPath());
//                        break;
//                    }
//                }
//            }
//
//            rrLossTableRepository.save(conformedRRLT);
////            eltepHeaderRepository.save(confELTEPHeaders);
//            rrStatisticHeaderRepository.save(confRrStatisticHeaders);
//
//            // finish step 7 EXTRACT_CONFORMED_EPCURVE_STATS for one analysis in loop for of many analysis
//            Date endDate = new Date();
//            projectImportAssetLogA.setEndDate(endDate);
//            projectImportAssetLogRepository.save(projectImportAssetLogA);
//            log.info("Finish import progress STEP 7 : EXTRACT_CONFORMED_EPCURVE_STATS for analysis: {}", bundle.getSourceResult().getId());
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
    private EpCurveExtractResult mapFinancialPerspectiveToSummaryStats(List<String> fPs, RLAnalysis riskLinkAnalysis, Long defaultInstanceId, Long rdmId, String rdmName, Long analysisId) {
        EpCurveExtractResult result = new EpCurveExtractResult();
        fPs.forEach(fp -> {
            Map<StatisticMetric, List<AnalysisEpCurves>> metricToEPCurve = new HashMap<>();
            Long datasourceId = riskLinkAnalysis.getRlModelDataSourceId();
            RlModelDataSource datasource = dataSourceRepository.findById(datasourceId).orElseThrow(() -> new RuntimeException("No available datasource with ID " + datasourceId));
            Long instanceId = ofNullable(datasource).map(ds -> ds.getInstanceId()).orElse(defaultInstanceId);

            // @TODO : Get the treaty label regarding the FP Object
            rmsService.getAnalysisEpCurves(rdmId, rdmName, analysisId, fp, null)
                    .forEach(epCurve -> {
                        StatisticMetric metric = StatisticMetric.getFrom(epCurve.getEpTypeCode());
                        if (metricToEPCurve.containsKey(metric))
                            metricToEPCurve.get(metric).add(epCurve);
                        else
                            metricToEPCurve.put(metric, asList(epCurve));
                    });

            result.fpToELTEPCurves.put(fp,metricToEPCurve);

            // @TODO : Get the treaty label regarding the FP Object
            AnalysisSummaryStats summaryStats =
                    ofNullable(rmsService.getAnalysisSummaryStats(rdmId, rdmName, analysisId, fp, null))
                            .map(l -> l.get(0)).orElse(new AnalysisSummaryStats());
            result.fpToELTSumStat.put(fp, summaryStats);
        });
        return result;
    }

    private List<EPCurveHeader> generateEpCurveHeaders(List<String> fPs, EpCurveExtractResult epCurveExtractResult, RRAnalysis rrAnalysis, Long lossDataHeaderId) {
        return fPs.stream().map(fp -> {
            List<EPCurveHeader> epCurves = new ArrayList<>();
            Map<StatisticMetric, List<AnalysisEpCurves>> metricToEPCurves = epCurveExtractResult.fpToELTEPCurves.get(fp);
            metricToEPCurves.forEach((statisticMetric, analysisEpCurves) -> {
                String makeEpCurveFileName = makeEpCurveFileName(fp, rrAnalysis.getCreationDate(), ".bin");
                BinFile file = epCurveWriter.writeELTEPCurves(metricToEPCurves.get(statisticMetric), makeEpCurveFileName);
                epCurves.add(
                        EPCurveHeader.builder()
                                .ePCurveHeaderId(null)
                                .entity(1)
                                .financialPerspective(fp)
                                .statisticMetric(statisticMetric)
                                .lossDataType(StatisticsType.ELT.getCode())
                                .ePCurves(analysisEpCurves.toString()) // to be implemented
                                .ePCFileName(makeEpCurveFileName)
                                .ePCFilePath(file.getPath())
                                .lossDataId(lossDataHeaderId)
                                .build()
                );
            });

            return epCurves;
        }).flatMap(List::stream).collect(toList());
    }

    private List<SummaryStatisticHeader> generateSummaryStatsHeader(List<String> fPs, EpCurveExtractResult epCurveExtractResult, Long lossDataHeaderId) {
        return fPs.stream().map(fp -> {
            AnalysisSummaryStats summaryStats = epCurveExtractResult.fpToELTSumStat.get(fp);
            //@TODO Review
            String fileName = makeEpSummaryStatFileName(fp, new Date(), ".bin");
            BinFile file = epSummaryStatWriter.writePLTSummaryStatistics(epCurveExtractResult.fpToELTSumStat.get(fp), fileName);
            return new SummaryStatisticHeader(1, fp, summaryStats.getCov(), summaryStats.getStdDev(),
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

}
