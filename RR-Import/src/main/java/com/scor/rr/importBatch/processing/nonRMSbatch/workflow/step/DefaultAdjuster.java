/*
package com.scor.rr.importBatch.processing.nonRMSbatch.workflow.step;

import com.scor.almf.ihub.treaty.processing.adjustment.DefaultAdjustmentRequest;
import com.scor.almf.ihub.treaty.processing.nonRMSbatch.bean.BaseNonRMSBean;
import com.scor.almf.ihub.treaty.processing.nonRMSbatch.workflow.bundle.PLTBundleNonRMS;
import com.scor.almf.ihub.treaty.processing.nonRMSbatch.workflow.bundle.TransformationBundleNonRMS;
import com.scor.almf.ihub.treaty.processing.nonRMSbatch.workflow.bundle.TransformationPackageNonRMS;
import com.scor.almf.treaty.cdm.domain.dss.agnostic.tracking.ProjectImportAssetLog;
import com.scor.almf.treaty.cdm.domain.plt.ScorPLTHeader;
import com.scor.almf.treaty.cdm.domain.rap.TargetRap;
import com.scor.almf.treaty.cdm.repository.dss.agnostic.ProjectImportAssetLogRepository;
import com.scor.almf.treaty.cdm.repository.rap.TargetRapRepository;
import com.scor.almf.treaty.cdm.tools.sequence.MongoDBSequence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Date;

*/
/**
 * Created by U005342 on 16/07/2018.
 *//*

public class DefaultAdjuster extends BaseNonRMSBean {

    private static final Logger log = LoggerFactory.getLogger(DefaultAdjuster.class);

    @Autowired
    private MongoDBSequence mongoDBSequence;

    @Autowired
    ProjectImportAssetLogRepository projectImportAssetLogRepository;

    @Autowired
    TargetRapRepository targetRapRepository;

    private final String defAdjNonRMSURL;

    private TransformationPackageNonRMS transformationPackage;

    public DefaultAdjuster(String batchRestHost, String batchRestPort, String batchRestDefAdj) {
        final URI uri = UriComponentsBuilder.newInstance().scheme("http").host(batchRestHost).port(Integer.parseInt(batchRestPort)).build().toUri();
        defAdjNonRMSURL = UriComponentsBuilder.fromUri(uri).path(batchRestDefAdj).build().toString();
    }

    public Boolean adjustDefault() {

        log.debug("Start ADJUST_DEFAULT");
        Date startDate = new Date();

        for (TransformationBundleNonRMS bundle : transformationPackage.getBundles()) {
            // start new step (import progress) : step 5 ADJUST_DEFAULT for this analysis in loop of many analysis :
            ProjectImportAssetLog projectImportAssetLogA = new ProjectImportAssetLog();
            mongoDBSequence.nextSequenceId(projectImportAssetLogA);
            projectImportAssetLogA.setProjectId(getProjectId());
            projectImportAssetLogA.setStepId(5);
            projectImportAssetLogA.setStepName(StepNonRMS.getStepNameFromStepIdForAnalysis(projectImportAssetLogA.getStepId()));
            projectImportAssetLogA.setStartDate(startDate);
            projectImportAssetLogA.setProjectImportLogId(bundle.getProjectImportLogAnalysisId());
            // --------------------------------------------------------------

            if (bundle.getPltBundles() == null) {
                projectImportAssetLogA.getErrorMessages().add(String.format("ERROR no PLTs found", bundle.getRrAnalysis().getId()));
                Date endDate = new Date();
                projectImportAssetLogA.setEndDate(endDate);
                projectImportAssetLogRepository.save(projectImportAssetLogA);
                log.info("Finish import progress STEP 5 : ADJUST_DEFAULT for analysis: {}", bundle.getRrAnalysis().getId());
                continue;
            }

            log.info("{} threads", bundle.getPltBundles().size());
            for (PLTBundleNonRMS pltBundle : bundle.getPltBundles()) {
                ScorPLTHeader pltHeader = pltBundle.getHeader();
                TargetRap targetRap = pltHeader.getTargetRap();
                if (targetRap.getTargetRapId() == null || targetRap.getTargetRapCode() == null) {
                    targetRap = targetRapRepository.findOne(targetRap.getId());
                }

                String projectId = pltHeader.getProject().getId();
                String pltHeaderId = pltHeader.getId();
//                Long analysisId = bundle.getRmsAnalysis().getAnalysisId();
//                String analysisName = bundle.getRmsAnalysis().getAnalysisName();
                Long analysisId = bundle.getRrAnalysis().getAnalysisId();
                String analysisName = bundle.getRrAnalysis().getAnalysisName();
                Integer targetRapId = targetRap.getTargetRapId();
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
            Date endDate = new Date();
            projectImportAssetLogA.setEndDate(endDate);
            projectImportAssetLogRepository.save(projectImportAssetLogA);
            log.info("Finish import progress STEP 5 : ADJUST_DEFAULT for RRAnalysis : {}", bundle.getRrAnalysis().getId());
        }

        return true;
    }

    public TransformationPackageNonRMS getTransformationPackage() {
        return transformationPackage;
    }

    public void setTransformationPackage(TransformationPackageNonRMS transformationPackage) {
        this.transformationPackage = transformationPackage;
    }
}
*/
