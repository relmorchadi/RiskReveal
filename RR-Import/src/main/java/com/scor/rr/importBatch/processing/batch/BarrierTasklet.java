package com.scor.rr.importBatch.processing.batch;


import com.scor.rr.domain.entities.rms.ProjectImportAssetLog;
import com.scor.rr.importBatch.processing.treaty.TransformationBundle;
import com.scor.rr.importBatch.processing.treaty.TransformationPackage;
import com.scor.rr.repository.rms.ProjectImportAssetLogRepository;
import com.scor.rr.utils.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;

/**
 * Created by U002629 on 26/06/2015.
 */
public class BarrierTasklet implements ALMFBarrier {

    private static final Logger log = LoggerFactory.getLogger(BarrierTasklet.class);
    private LockingSemaphore lockingSemaphore;

    @Autowired
    private TransformationPackage transformationPackage;

//    @Autowired
//    private MongoDBSequence mongoDBSequence;

    @Autowired
    private ProjectImportAssetLogRepository projectImportAssetLogRepository;

    public void setLockingSemaphore(LockingSemaphore lockingSemaphore) {
        this.lockingSemaphore = lockingSemaphore;
    }

    @Override
    public Boolean acquire() throws InterruptedException {
        Date startDate = new Date((new java.util.Date()).getTime());
        for (TransformationBundle bundle : transformationPackage.getBundles()) {
            // start new step (import progress) : step 11 CLOSE_BARRIER for this analysis in loop of many analysis :
            ProjectImportAssetLog projectImportAssetLogA = new ProjectImportAssetLog();
            projectImportAssetLogA.setProjectId(transformationPackage.getProjectId());
            projectImportAssetLogA.setStepId(11);
            projectImportAssetLogA.setStepName(Step.getStepNameFromStepIdForAnalysis(projectImportAssetLogA.getStepId()));
            projectImportAssetLogA.setStartDate(startDate);
            projectImportAssetLogA.setProjectImportLogId(bundle.getProjectImportLogAnalysisId());

            // finis step 11 CLOSE_BARRIER for one analysis in loop for of many analysis
            Date endDate = new Date((new java.util.Date()).getTime());
            projectImportAssetLogA.setEndDate(endDate);
            projectImportAssetLogRepository.save(projectImportAssetLogA);
            log.info("Finish import progress STEP 11 : CLOSE_BARRIER for analysis: {}", bundle.getSourceResult().getSourceResultId());
        }

        return lockingSemaphore.acquire();
    }

    @Override
    public Boolean release() {
        Date startDate = new Date((new java.util.Date()).getTime());
        for (TransformationBundle bundle : transformationPackage.getBundles()) {
            // start new step (import progress) : step 13 OPEN_OR_ERROR_BARRIER for this analysis in loop of many analysis :
            ProjectImportAssetLog projectImportAssetLogA = new ProjectImportAssetLog();
            projectImportAssetLogA.setProjectId(transformationPackage.getProjectId());
            projectImportAssetLogA.setStepId(13);
            projectImportAssetLogA.setStepName(Step.getStepNameFromStepIdForAnalysis(projectImportAssetLogA.getStepId()));
            projectImportAssetLogA.setStartDate(startDate);
            projectImportAssetLogA.setProjectImportLogId(bundle.getProjectImportLogAnalysisId());

            // finis step 13 OPEN_OR_ERROR_BARRIER for one analysis in loop for of many analysis
            Date endDate = new Date((new java.util.Date()).getTime());
            projectImportAssetLogA.setEndDate(endDate);
            projectImportAssetLogRepository.save(projectImportAssetLogA);
            log.info("Finish import progress STEP 13 : OPEN_OR_ERROR_BARRIER for analysis: {}", bundle.getSourceResult().getSourceResultId());
        }

        lockingSemaphore.release();
        return true;
    }
}
