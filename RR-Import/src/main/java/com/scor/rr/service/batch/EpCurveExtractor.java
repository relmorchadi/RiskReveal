package com.scor.rr.service.batch;

import com.scor.rr.domain.AnalysisEpCurves;
import com.scor.rr.domain.AnalysisSummaryStats;
import com.scor.rr.domain.dto.BinFile;
import com.scor.rr.domain.enums.FinancialPerspectiveCodeEnum;
import com.scor.rr.domain.enums.StatisticMetric;
import com.scor.rr.domain.enums.StatisticsType;
import com.scor.rr.domain.model.EPCurveHeader;
import com.scor.rr.domain.model.LossDataHeader;
import com.scor.rr.domain.model.SummaryStatisticHeader;
import com.scor.rr.domain.riskLink.DefaultReturnPeriod;
import com.scor.rr.domain.riskLink.RLAnalysis;
import com.scor.rr.domain.riskLink.RlModelDataSource;
import com.scor.rr.domain.riskLink.RlSourceEpHeader;
import com.scor.rr.domain.riskReveal.RRAnalysis;
import com.scor.rr.repository.*;
import com.scor.rr.service.RmsService;
import com.scor.rr.service.batch.writer.EpCurveWriter;
import com.scor.rr.service.batch.writer.EpSummaryStatWriter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
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

    /**
     * Extract EP Curve Tasklet main entry
     *
     * @return RepeatStatus
     */
    public RepeatStatus extractEpCurve() {
        // Given some transformation bundles

        List<Map> transformationBundle = new ArrayList<>();

        transformationBundle.forEach(bundle -> {
            RLAnalysis riskLinkAnalysis = (RLAnalysis) bundle.get("rlAnalysis");
            String instanceId = (String) bundle.get("instanceId");
            RRAnalysis rrAnalysis = (RRAnalysis) bundle.get("rrAnalysis");
            List<RlSourceEpHeader> epHeaders = riskLinkAnalysis.getRlSourceEpHeaders();

            BigInteger analysisId = riskLinkAnalysis.getRlAnalysisId();
            BigInteger rdmId = riskLinkAnalysis.getRdmId();
            String rdmName = riskLinkAnalysis.getRdmName();

            LossDataHeader lossDataHeader = (LossDataHeader) bundle.get("lossDataHeader");

            // @TODO: Where could we track the selected Financial Perspective

            List<String> sourceFPs = epHeaders.stream().map(item -> item.getFinancialPerpective()).collect(toList());
            List<String> filteredFPs = sourceFPs.stream().filter(fp -> !StringUtils.equalsIgnoreCase(fp, FinancialPerspectiveCodeEnum.TY.getCode())).collect(toList());

            // @TODO: Add the selected FP if eq. to 'TY' (to check this logic with Viet)

            EpCurveExtractResult epCurveExtractResult = this.mapFinancialPerspToSummaryStats(filteredFPs, riskLinkAnalysis, instanceId, rdmId.intValue(), rdmName, analysisId.intValue());

            lossDataHeaderRepository.save(lossDataHeader);
            Long lossDataHeaderId = lossDataHeader.getLossDataHeaderId();

            epCurveHeaderRepository.saveAll(
                    this.generateEpCurveHeaders(filteredFPs, epCurveExtractResult, rrAnalysis, lossDataHeaderId)
            );
            summaryStatisticHeaderRepository.saveAll(
                    this.generateSummayStatsHeader(filteredFPs, epCurveExtractResult, lossDataHeaderId)
            );

        });


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
    private EpCurveExtractResult mapFinancialPerspToSummaryStats(List<String> fPs, RLAnalysis riskLinkAnalysis, String defaultInstanceId, Integer rdmId, String rdmName, Integer analysisId) {
        EpCurveExtractResult result = new EpCurveExtractResult();
        fPs.forEach(fp -> {
            Map<StatisticMetric, List<AnalysisEpCurves>> metricToEPCurve = new HashMap<>();
            Integer datasourceId = riskLinkAnalysis.getRlModelDataSourceId();
            RlModelDataSource datasource = dataSourceRepository.findById(datasourceId).orElseThrow(() -> new RuntimeException("No available datasource with ID " + datasourceId));
            String instanceId = ofNullable(datasource).map(ds -> ds.getInstanceId()).orElse(defaultInstanceId);

            // @TODO : Get the treaty label regarding the FP Object
            rmsService.getAnalysisEpCurves(rdmId, rdmName, analysisId, fp, null)
                    .forEach(epCurve -> {
                        StatisticMetric metric = StatisticMetric.getFrom(epCurve.getEpTypeCode());
                        if (metricToEPCurve.containsKey(metric))
                            metricToEPCurve.get(metric).add(epCurve);
                        else
                            metricToEPCurve.put(metric, asList(epCurve));
                    });

            result.fpToELTEPCurves.put(fp, interpolateEltEPCurve(metricToEPCurve));

            // @TODO : Get the treaty label regarding the FP Object
            AnalysisSummaryStats summaryStats =
                    ofNullable(rmsService.getAnalysisSummaryStats(rdmId, rdmName, analysisId, fp, null))
                            .map(l -> l.get(0)).orElse(new AnalysisSummaryStats().builder().cov(0).purePremium(0d).stdDev(0d).build());
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


    private List<SummaryStatisticHeader> generateSummayStatsHeader(List<String> fPs, EpCurveExtractResult epCurveExtractResult, Long lossDataHeaderId) {
        return fPs.stream().map(fp -> {
            AnalysisSummaryStats summaryStats = epCurveExtractResult.fpToELTSumStat.get(fp);
            //@TODO Review
            String fileName = makeEpSummaryStatFileName(fp, new Date(), ".bin");
            BinFile file = epSummaryStatWriter.writePLTSummaryStatistics(epCurveExtractResult.fpToELTSumStat.get(fp), fileName);
            return SummaryStatisticHeader.builder()
                    .entity(1)
                    .financialPerspective(fp)
                    .cov(summaryStats.getCov())
                    .standardDeviation(summaryStats.getStdDev())
                    .purePremium(summaryStats.getPurePremium())
                    .lossDataType(StatisticsType.ELT.getCode())
                    .lossDataId(lossDataHeaderId)
                    .ePSFileName(fileName)
                    .ePSFilePath(file.getPath())
                    .build();
        }).collect(toList());
    }


    private Map<StatisticMetric, List<AnalysisEpCurves>> interpolateEltEPCurve(Map<StatisticMetric, List<AnalysisEpCurves>> metricToEPCurve) {
        Map<StatisticMetric, List<AnalysisEpCurves>> metricToDrpEPCurve = new HashMap<>();
        List<DefaultReturnPeriod> drpList = defaultReturnPeriodRepository.findAll();
        List<Double> epList = new ArrayList<>(drpList.size());
        for (DefaultReturnPeriod drp : drpList) {
            epList.add(drp.getExcedanceProbability());
        }

        for (Map.Entry<StatisticMetric, List<AnalysisEpCurves>> entry : metricToEPCurve.entrySet()) {
            List<AnalysisEpCurves> drpELTEPCurve = new ArrayList<>(epList.size());

            List<Double> ascKeys = new ArrayList<>(entry.getValue().size());
            List<Double> values = new ArrayList<>(entry.getValue().size());

            for (AnalysisEpCurves eltepCurve : entry.getValue()) {
                ascKeys.add(eltepCurve.getExeceedanceProb().doubleValue());
                values.add(eltepCurve.getLoss());
            }

            List<Double> loss = lerp(epList, ascKeys, values);

            for (int i = 0; i < epList.size(); ++i) {
                drpELTEPCurve.add(new AnalysisEpCurves(BigDecimal.valueOf(epList.get(i).longValue()), loss.get(i)));
            }
            metricToDrpEPCurve.put(entry.getKey(), drpELTEPCurve);
        }

        return metricToDrpEPCurve;
    }

    private List<Double> lerp(List<Double> inputs, List<Double> ascKeys, List<Double> values) {
        List<Double> out = new ArrayList<>();
        for (Double input : inputs) {
            int idx = Collections.binarySearch(ascKeys, input);
            double interped;
            if (idx == -1) { // under the referenced range
                interped = values.get(0);
            } else if (idx == -1 - ascKeys.size()) { // beyond the referenced range
                interped = values.get(ascKeys.size() - 1);
            } else if (idx >= 0) { // it matches one endpoint in RP input list
                interped = values.get(idx);
            } else { // it falls into an interval
                int lowIdx = Math.abs(idx + 2);
                int highIdx = Math.abs(idx + 1);
                Double loKey = ascKeys.get(lowIdx);
                Double hiKey = ascKeys.get(highIdx);
                Double loValue = values.get(lowIdx);
                Double hiValue = values.get(highIdx);

                interped = (input - loKey) * (hiValue - loValue) / (hiKey - loKey) + loValue;
            }
            out.add(interped);
        }
        return out;
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
