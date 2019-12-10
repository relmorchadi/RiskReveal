package com.scor.rr.service.batch;

import com.scor.rr.domain.AdjustmentThreadEntity;
import com.scor.rr.domain.PltHeaderEntity;
import com.scor.rr.domain.dto.EPMetric;
import com.scor.rr.domain.dto.PLTBundle;
import com.scor.rr.domain.dto.adjustement.AdjustmentThreadCreationRequest;
import com.scor.rr.service.state.TransformationBundle;
import com.scor.rr.service.state.TransformationPackage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@StepScope
@Service
@Slf4j
public class DefaultAdjustment {

    @Autowired
    private TransformationPackage transformationPackage;

    @Value(value = "${thread.creation.service}")
    private String threadCreationURL;

    @Value(value = "${thread.calculation.service}")
    private String threadCalculationURL;

    public RepeatStatus defaultAdjustment() {

        log.debug("Starting default adjustment");

        for (TransformationBundle bundle : transformationPackage.getTransformationBundles()) {

            if (bundle.getPltBundles() != null) {

                RestTemplate restTemplate = new RestTemplate();
                for (PLTBundle pltBundle : bundle.getPltBundles()) {
                    if (!pltBundle.getPltError()) {

                        HttpEntity<AdjustmentThreadCreationRequest> createThreadRequest =
                                new HttpEntity<>(new AdjustmentThreadCreationRequest(pltBundle.getHeader().getPltHeaderId(), "", true));

                        ResponseEntity<AdjustmentThreadEntity> response = restTemplate
                                .exchange(threadCreationURL, HttpMethod.POST, createThreadRequest, AdjustmentThreadEntity.class);

                        if (response.getStatusCode().equals(HttpStatus.OK)) {
                            AdjustmentThreadEntity adjustmentThreadEntity = response.getBody();

                            if (adjustmentThreadEntity != null && adjustmentThreadEntity.getAdjustmentThreadId() != null) {

                                HttpEntity<Integer> calculateThreadRequest =
                                        new HttpEntity<>(adjustmentThreadEntity.getAdjustmentThreadId());

                                ResponseEntity<PltHeaderEntity> calculationResponse = restTemplate
                                        .exchange(threadCalculationURL, HttpMethod.POST, calculateThreadRequest, PltHeaderEntity.class);

                                if (calculationResponse.getStatusCode().equals(HttpStatus.OK))
                                    log.info("Calculation for thread has ended successfully");
                                else
                                    log.error("An error has occurred while calculating for thread with id {}", adjustmentThreadEntity.getAdjustmentThreadId());

                                this.insertStatsForPlt(pltBundle.getHeader(), restTemplate);
                            }
                        } else {
                            log.error("An error has occurred {}", response.getStatusCodeValue());
                        }
                    }
                }

            } else {
                log.error("ERROR : no PLTs were found");
            }
        }

        log.debug("Default adjustment completed");

        return RepeatStatus.FINISHED;
    }

    private void insertStatsForPlt(PltHeaderEntity plt, RestTemplate restTemplate){
        String fullFilePath = plt.getLossDataFilePath() + "/" + plt.getLossDataFileName();

        // @INFO : AEPMetric request/response config

        HttpEntity<String> aepMetricRequest = new HttpEntity<>(fullFilePath);

        ResponseEntity<EPMetric> aepMetricResponse = restTemplate
                .exchange(threadCreationURL, HttpMethod.GET, aepMetricRequest, EPMetric.class);

        // @INFO : OEPMetric request/response config

        HttpEntity<String> oepMetricRequest = new HttpEntity<>(fullFilePath);

        ResponseEntity<EPMetric> oepMetricResponse = restTemplate
                .exchange(threadCreationURL, HttpMethod.GET, oepMetricRequest, EPMetric.class);

        if (aepMetricResponse.getStatusCode().equals(HttpStatus.OK)) {
            EPMetric epMetric = aepMetricResponse.getBody();
            if (epMetric != null && epMetric.getEpMetricPoints() != null && !epMetric.getEpMetricPoints().isEmpty()) {
                epMetric.getEpMetricPoints().forEach(epMetricPoint -> {

                });
            }
        } else {
            log.error("An error has occurred in the service /api/nodeProcessing/adjustThread/aepMetric {}", aepMetricResponse.getStatusCodeValue());
        }

        if (oepMetricResponse.getStatusCode().equals(HttpStatus.OK)) {
            EPMetric epMetric = oepMetricResponse.getBody();
            if (epMetric != null && epMetric.getEpMetricPoints() != null && !epMetric.getEpMetricPoints().isEmpty()) {
                epMetric.getEpMetricPoints().forEach(epMetricPoint -> {

                });
            }
        } else {
            log.error("An error has occurred in the service /api/nodeProcessing/adjustThread/oepMetric {}", oepMetricResponse.getStatusCodeValue());
        }
    }
}
