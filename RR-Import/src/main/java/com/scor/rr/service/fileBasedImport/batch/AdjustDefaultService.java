package com.scor.rr.service.fileBasedImport.batch;

import com.scor.rr.domain.PltHeaderEntity;
import com.scor.rr.domain.TargetRapEntity;
import com.scor.rr.repository.TargetRapRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final String defAdjNonRMSURL;

    private TransformationPackageNonRMS transformationPackage;

    public DefaultAdjuster(String batchRestHost, String batchRestPort, String batchRestDefAdj) {
        final URI uri = UriComponentsBuilder.newInstance().scheme("http").host(batchRestHost).port(Integer.parseInt(batchRestPort)).build().toUri();
        defAdjNonRMSURL = UriComponentsBuilder.fromUri(uri).path(batchRestDefAdj).build().toString();
    }



    public RepeatStatus adjustDefault() throws Exception {

        log.debug("Start ADJUST_DEFAULT");
//        Date startDate = new Date();

        for (TransformationBundleNonRMS bundle : transformationPackage.getBundles()) {
            // start new step (import progress) : step 5 ADJUST_DEFAULT for this analysis in loop of many analysis :
//            ProjectImportAssetLog projectImportAssetLogA = new ProjectImportAssetLog();
//            mongoDBSequence.nextSequenceId(projectImportAssetLogA);
//            projectImportAssetLogA.setProjectId(getProjectId());
//            projectImportAssetLogA.setStepId(5);
//            projectImportAssetLogA.setStepName(StepNonRMS.getStepNameFromStepIdForAnalysis(projectImportAssetLogA.getStepId()));
//            projectImportAssetLogA.setStartDate(startDate);
//            projectImportAssetLogA.setProjectImportLogId(bundle.getProjectImportLogAnalysisId());
            // --------------------------------------------------------------

//            if (bundle.getPltBundles() == null) {
//                projectImportAssetLogA.getErrorMessages().add(String.format("ERROR no PLTs found", bundle.getRrAnalysis().getId()));
//                Date endDate = new Date();
//                projectImportAssetLogA.setEndDate(endDate);
//                projectImportAssetLogRepository.save(projectImportAssetLogA);
//                log.info("Finish import progress STEP 5 : ADJUST_DEFAULT for analysis: {}", bundle.getRrAnalysis().getId());
//                continue;
//            }

            log.info("{} threads", bundle.getPltBundles().size());
            for (PLTBundleNonRMS pltBundle : bundle.getPltBundles()) {
                PltHeaderEntity pltHeader = pltBundle.getHeader();
                TargetRapEntity targetRap = targetRapRepository.findById(pltHeader.getTargetRAPId()).get();

                String projectId = pltHeader.getProjectId().toString();
                String pltHeaderId = pltHeader.getPltHeaderId().toString();
//                Long analysisId = bundle.getRmsAnalysis().getAnalysisId();
//                String analysisName = bundle.getRmsAnalysis().getAnalysisName();
                Long analysisId = bundle.getRrAnalysis().getAnalysisId();
                String analysisName = bundle.getRrAnalysis().getAnalysisName();
                Integer targetRapId = targetRap.getTargetRAPId().intValue();
                String sourceConfigVendor = "NonRMS";

                DefaultAdjustmentRequest defAdjRequest = new DefaultAdjustmentRequest(pltHeaderId);
                defAdjRequest.setProjectId(projectId);
                defAdjRequest.setAnalysisId(analysisId);
                defAdjRequest.setAnalysisName(analysisName);
//                defAdjRequest.setRdmId(rdmId);
//                defAdjRequest.setRdmName(rdm);
                defAdjRequest.setTargetRapId(targetRapId);

                RestTemplate restTemplate = new RestTemplate();
                final Integer response = restTemplate.postForObject(defAdjNonRMSURL, null, Integer.class,
                        projectId, pltHeaderId, "", "", "", "", targetRapId, sourceConfigVendor);

//                log.info("rdmName {}, rdmId {}, TargetRAP {} found, 100k-PLT {}", rdm, rdmId, targetRap.getTargetRapId(), pltHeader100k.getId());
                log.info("Response from default adjustment: {}", response);
            }

            // finis step 5 ADJUST_DEFAULT for one analysis in loop for of many analysis
//            Date endDate = new Date();
//            projectImportAssetLogA.setEndDate(endDate);
//            projectImportAssetLogRepository.save(projectImportAssetLogA);
            log.info("Finish import progress STEP 5 : ADJUST_DEFAULT for RRAnalysis : {}", bundle.getRrAnalysis().getRrAnalysisId());
        }

        return RepeatStatus.FINISHED;
    }

    public TransformationPackageNonRMS getTransformationPackage() {
        return transformationPackage;
    }

    public void setTransformationPackage(TransformationPackageNonRMS transformationPackage) {
        this.transformationPackage = transformationPackage;
    }

}
