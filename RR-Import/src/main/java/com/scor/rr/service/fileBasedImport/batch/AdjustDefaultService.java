package com.scor.rr.service.fileBasedImport.batch;

import com.scor.rr.domain.AdjustmentThread;
import com.scor.rr.domain.PltHeaderEntity;
import com.scor.rr.domain.SummaryStatisticHeaderEntity;
import com.scor.rr.domain.TargetRapEntity;
import com.scor.rr.domain.dto.PLTBundle;
import com.scor.rr.domain.dto.adjustement.AdjustmentThreadCreationRequest;
import com.scor.rr.repository.TargetRapRepository;
import com.scor.rr.service.state.TransformationBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Date;

@Service
@StepScope
public class AdjustDefaultService {

    private static final Logger log = LoggerFactory.getLogger(AdjustDefaultService.class);

    @Autowired
    TargetRapRepository targetRapRepository;

    @Autowired
    private TransformationPackageNonRMS transformationPackage;

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



    public RepeatStatus adjustDefault() throws Exception {
        log.debug("Starting default adjustment");
        for (TransformationBundleNonRMS bundle : transformationPackage.getBundles()) {
            if (bundle.getPltBundles() != null) {
                RestTemplate restTemplate = new RestTemplate();
                for (PLTBundleNonRMS pltBundle : bundle.getPltBundles()) {
                    //if (!pltBundle.getPltError()) { @TODO Review
                    if (true) {

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

    public TransformationPackageNonRMS getTransformationPackage() {
        return transformationPackage;
    }

    public void setTransformationPackage(TransformationPackageNonRMS transformationPackage) {
        this.transformationPackage = transformationPackage;
    }

}
