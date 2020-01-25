package com.scor.rr.service.batch;

import com.google.gson.Gson;
import com.scor.rr.configuration.file.BinFile;
import com.scor.rr.domain.*;
import com.scor.rr.domain.dto.EPMetric;
import com.scor.rr.domain.dto.PLTBundle;
import com.scor.rr.domain.dto.SummaryStatisticType;
import com.scor.rr.domain.dto.adjustement.AdjustmentThreadCreationRequest;
import com.scor.rr.domain.enums.PLTPublishStatus;
import com.scor.rr.domain.enums.StatisticsType;
import com.scor.rr.domain.enums.XLTOT;
import com.scor.rr.repository.EPCurveHeaderEntityRepository;
import com.scor.rr.repository.ModelAnalysisEntityRepository;
import com.scor.rr.repository.RegionPerilRepository;
import com.scor.rr.repository.SummaryStatisticHeaderRepository;
import com.scor.rr.service.batch.writer.AbstractWriter;
import com.scor.rr.service.batch.writer.EpCurveWriter;
import com.scor.rr.service.batch.writer.EpSummaryStatWriter;
import com.scor.rr.service.state.TransformationBundle;
import com.scor.rr.service.state.TransformationPackage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@StepScope
@Service
@Slf4j
public class DefaultAdjustment extends AbstractWriter {

    @Autowired
    private TransformationPackage transformationPackage;

    @Autowired
    private EpCurveWriter epCurveWriter;

    @Autowired
    private EpSummaryStatWriter epSummaryStatWriter;

    @Autowired
    private ModelAnalysisEntityRepository modelAnalysisEntityRepository;

    @Autowired
    private RegionPerilRepository regionPerilRepository;

    @Autowired
    private EPCurveHeaderEntityRepository epCurveHeaderEntityRepository;

    @Autowired
    private SummaryStatisticHeaderRepository summaryStatisticHeaderRepository;

    @Value(value = "${thread.creation.service}")
    private String threadCreationURL;

    @Value(value = "${thread.calculation.service}")
    private String threadCalculationURL;

    @Value(value = "${thread.calculation.service.aep}")
    private String aEPMetricURL;

    @Value(value = "${thread.calculation.service.oep}")
    private String oEPMetricURL;

    @Value(value = "${thread.calculation.service.aepTvAR}")
    private String aEPTvARMetricURL;

    @Value(value = "${thread.calculation.service.oepTvAR}")
    private String oEPTvARMetricURL;

    @Value(value = "${thread.calculation.service.summary}")
    private String summaryStatURL;


    public RepeatStatus defaultAdjustment() {

        log.debug("Starting default adjustment");

        for (TransformationBundle bundle : transformationPackage.getTransformationBundles()) {

            if (bundle.getPltBundles() != null) {

                RestTemplate restTemplate = new RestTemplate();
                for (PLTBundle pltBundle : bundle.getPltBundles()) {
                    if (!pltBundle.getPltError()) {

                        HttpEntity<AdjustmentThreadCreationRequest> createThreadRequest =
                                new HttpEntity<>(new AdjustmentThreadCreationRequest(pltBundle.getHeader().getPltHeaderId(), "", true));

                        ResponseEntity<AdjustmentThread> response = restTemplate
                                .exchange(threadCreationURL, HttpMethod.POST, createThreadRequest, AdjustmentThread.class);

                        if (response.getStatusCode().equals(HttpStatus.OK)) {
                            AdjustmentThread adjustmentThread = response.getBody();

                            if (adjustmentThread != null && adjustmentThread.getAdjustmentThreadId() != null) {

                                HttpHeaders requestHeaders = new HttpHeaders();
                                requestHeaders.add("Accept", MediaType.APPLICATION_JSON_VALUE);

                                HttpEntity<String> request = new HttpEntity<>(requestHeaders);

                                UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(threadCalculationURL)
                                        .queryParam("threadId", adjustmentThread.getAdjustmentThreadId());

                                ResponseEntity<PltHeaderEntity> calculationResponse = restTemplate
                                        .exchange(uriBuilder.toUriString(), HttpMethod.POST, request, PltHeaderEntity.class);

                                if (calculationResponse.getStatusCode().equals(HttpStatus.OK)) {
                                    log.info("Calculation for thread has ended successfully");
                                    //this.getAndWriteStatsForPlt(adjustmentThread.getFinalPLT(), restTemplate, true, adjustmentThread.getAdjustmentThreadId());
                                } else {
                                    log.error("An error has occurred while calculating for thread with id {}", adjustmentThread.getAdjustmentThreadId());
                                }
                            }
                            //this.getAndWriteStatsForPlt(pltBundle.getHeader(), restTemplate, false, null);
                        } else {
                            log.error("An error has occurred {}", response.getStatusCodeValue());
                        }
                        this.calculateSummaryStat(pltBundle.getHeader(), restTemplate);
                    }
                }

            } else {
                log.error("ERROR : no PLTs were found");
            }
        }

        log.debug("Default adjustment completed");

        return RepeatStatus.FINISHED;
    }

    private void calculateSummaryStat(PltHeaderEntity pltHeader, RestTemplate restTemplate) {

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Accept", MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<String> request = new HttpEntity<>(requestHeaders);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(summaryStatURL)
                .queryParam("pltId", pltHeader.getPltHeaderId());

        ResponseEntity<SummaryStatisticHeaderEntity> response = restTemplate
                .exchange(uriBuilder.toUriString(), HttpMethod.POST, request, SummaryStatisticHeaderEntity.class);

        if (response.getStatusCode().equals(HttpStatus.OK))
            log.info("stats calculation has ended successfully for the PLT with Id {}", pltHeader.getPltHeaderId());
        else
            log.info("stats calculation has failed for the PLT with Id {}", pltHeader.getPltHeaderId());
    }

    private void getAndWriteStatsForPlt(PltHeaderEntity pltHeader, RestTemplate restTemplate, boolean isThread, Integer threadId) {

        String fullFilePath = pltHeader.getLossDataFilePath() + "/" + pltHeader.getLossDataFileName();

        Optional<ModelAnalysisEntity> modelAnalysisOptional = modelAnalysisEntityRepository.findById(pltHeader.getModelAnalysisId());
        Optional<RegionPerilEntity> regionPerilEntityOptional = regionPerilRepository.findById(pltHeader.getRegionPerilId());

        if (modelAnalysisOptional.isPresent() && regionPerilEntityOptional.isPresent()) {

            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.add("Accept", MediaType.APPLICATION_JSON_VALUE);

            HttpEntity<String> request = new HttpEntity<>(requestHeaders);

            ResponseEntity<EPMetric> aepMetricResponse = this.getEpStats(aEPMetricURL, request, fullFilePath, restTemplate);
            ResponseEntity<EPMetric> oepMetricResponse = this.getEpStats(oEPMetricURL, request, fullFilePath, restTemplate);
            ResponseEntity<EPMetric> aepTVarMetricResponse = this.getEpStats(aEPTvARMetricURL, request, fullFilePath, restTemplate);
            ResponseEntity<EPMetric> oepTVarMetricResponse = this.getEpStats(oEPTvARMetricURL, request, fullFilePath, restTemplate);

            if (aepMetricResponse.getStatusCode().equals(HttpStatus.OK))
                writeEPStat(pltHeader, modelAnalysisOptional.get(), regionPerilEntityOptional.get(), aepMetricResponse, isThread, threadId);
            else
                log.error("An error has occurred in the service /api/nodeProcessing/adjustThread/aepMetric {}", aepMetricResponse.getStatusCodeValue());


            if (oepMetricResponse.getStatusCode().equals(HttpStatus.OK))
                writeEPStat(pltHeader, modelAnalysisOptional.get(), regionPerilEntityOptional.get(), oepMetricResponse, isThread, threadId);
            else
                log.error("An error has occurred in the service /api/nodeProcessing/adjustThread/oepMetric {}", oepMetricResponse.getStatusCodeValue());


            if (aepTVarMetricResponse.getStatusCode().equals(HttpStatus.OK))
                writeEPStat(pltHeader, modelAnalysisOptional.get(), regionPerilEntityOptional.get(), aepTVarMetricResponse, isThread, threadId);
            else
                log.error("An error has occurred in the service /api/nodeProcessing/adjustThread/aepMetric {}", aepMetricResponse.getStatusCodeValue());


            if (oepTVarMetricResponse.getStatusCode().equals(HttpStatus.OK))
                writeEPStat(pltHeader, modelAnalysisOptional.get(), regionPerilEntityOptional.get(), oepTVarMetricResponse, isThread, threadId);
            else
                log.error("An error has occurred in the service /api/nodeProcessing/adjustThread/aepMetric {}", aepMetricResponse.getStatusCodeValue());

            ResponseEntity<Double> averageAnnualLossResponse = this.getSummaryStats(summaryStatURL, request, fullFilePath, SummaryStatisticType.averageAnnualLoss, restTemplate);
            ResponseEntity<Double> covResponse = this.getSummaryStats(summaryStatURL, request, fullFilePath, SummaryStatisticType.coefOfVariance, restTemplate);
            ResponseEntity<Double> stdDevResponse = this.getSummaryStats(summaryStatURL, request, fullFilePath, SummaryStatisticType.stdDev, restTemplate);

            if (averageAnnualLossResponse.getStatusCode().equals(HttpStatus.OK) && averageAnnualLossResponse.getBody() != null &&
                    covResponse.getStatusCode().equals(HttpStatus.OK) && covResponse.getBody() != null &&
                    stdDevResponse.getStatusCode().equals(HttpStatus.OK) && stdDevResponse.getBody() != null) {

                this.writeSummaryStat(pltHeader, modelAnalysisOptional.get(), regionPerilEntityOptional.get(),
                        averageAnnualLossResponse.getBody(), covResponse.getBody(), stdDevResponse.getBody(), isThread, threadId);
            }
        } else {
            log.error("no model analysis found for plt with id {}", pltHeader.getPltHeaderId());
        }
    }

    private void writeEPStat(PltHeaderEntity pltHeader,
                             ModelAnalysisEntity modelAnalysis,
                             RegionPerilEntity regionPeril,
                             ResponseEntity<EPMetric> response,
                             boolean isThread,
                             Integer threadId) {
        EPMetric oepMetric = response.getBody();
        Gson gson = new Gson();

        if (oepMetric != null && oepMetric.getEpMetricPoints() != null && !oepMetric.getEpMetricPoints().isEmpty()) {

            String epCurveFilename = makePLTEPCurveFilename(
                    pltHeader.getCreatedDate(),
                    regionPeril.getRegionPerilCode(),
                    modelAnalysis.getFinancialPerspective(),
                    pltHeader.getCurrencyCode(),
                    XLTOT.TARGET,
                    pltHeader.getTargetRAPId(),
                    pltHeader.getPltSimulationPeriods(),
                    PLTPublishStatus.PURE,
                    isThread ? threadId : 0, // pure PLT
                    pltHeader.getPltHeaderId(),
                    modelAnalysis.getDivision(),
                    "bin");
            BinFile file = epCurveWriter.writePLTEPCurves(oepMetric.getEpMetricPoints(), epCurveFilename, oepMetric.getMetric(), modelAnalysis.getDivision());

            EPCurveHeaderEntity epCurveHeader = EPCurveHeaderEntity.builder()
                    .entity(1)
                    .lossDataType("PLT")
                    .statisticMetric(oepMetric.getMetric())
                    .ePCurves(gson.toJson(oepMetric.getEpMetricPoints()))
                    // TODO : Review this
                    .lossDataId(pltHeader.getPltHeaderId())
                    .financialPerspective(modelAnalysis.getFinancialPerspective())
                    .build();

            if (file != null) {
                epCurveHeader.setEPCFilePath(file.getPath());
                epCurveHeader.setEPCFileName(file.getFileName());
            }

            epCurveHeaderEntityRepository.save(epCurveHeader);
        }
    }

    private void writeSummaryStat(PltHeaderEntity pltHeader,
                                  ModelAnalysisEntity modelAnalysis,
                                  RegionPerilEntity regionPeril,
                                  double averageAnnualLoss,
                                  double cov,
                                  double stdDev,
                                  boolean isThread,
                                  Integer threadId) {

        String summaryStatFilename = makePLTSummaryStatFilename(
                pltHeader.getCreatedDate(),
                regionPeril.getRegionPerilCode(),
                modelAnalysis.getFinancialPerspective(),
                pltHeader.getCurrencyCode(),
                XLTOT.TARGET,
                pltHeader.getTargetRAPId(),
                pltHeader.getPltSimulationPeriods(),
                PLTPublishStatus.PURE,
                isThread ? threadId : 0, // pure PLT
                pltHeader.getPltHeaderId(),
                modelAnalysis.getDivision(),
                "bin");

        AnalysisSummaryStats analysisSummaryStats = new AnalysisSummaryStats();

        analysisSummaryStats.setPurePremium(averageAnnualLoss);
        analysisSummaryStats.setCov(cov);
        analysisSummaryStats.setStdDev(stdDev);

        BinFile file = epSummaryStatWriter.writeELTSummaryStatistics(analysisSummaryStats, summaryStatFilename, modelAnalysis.getDivision());

        // @TODO: review the pltHeaderId with the data modal

        SummaryStatisticHeaderEntity summaryStatisticHeaderEntity =
                new SummaryStatisticHeaderEntity(1L, modelAnalysis.getFinancialPerspective(), cov, stdDev,
                        averageAnnualLoss, StatisticsType.PLT.getCode(), pltHeader.getPltHeaderId(), summaryStatFilename, file.getPath());

        summaryStatisticHeaderRepository.save(summaryStatisticHeaderEntity);
    }

    private ResponseEntity<EPMetric> getEpStats(String url, HttpEntity request, String fullFilePath, RestTemplate restTemplate) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("pathToFile", fullFilePath);
        return restTemplate
                .exchange(uriBuilder.toUriString(), HttpMethod.GET, request, EPMetric.class);
    }

    private ResponseEntity<Double> getSummaryStats(String url, HttpEntity request, String fullFilePath, SummaryStatisticType type, RestTemplate restTemplate) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("pathToFile", fullFilePath)
                .queryParam("type", type.getValue());
        return restTemplate
                .exchange(uriBuilder.toUriString(), HttpMethod.GET, request, Double.class);
    }
}
