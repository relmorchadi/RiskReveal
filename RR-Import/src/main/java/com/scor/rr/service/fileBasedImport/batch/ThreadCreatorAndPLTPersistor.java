package com.scor.rr.service.fileBasedImport.batch;

import com.scor.almf.ihub.treaty.processing.nonRMSbatch.bean.BaseNonRMSBean;
import com.scor.almf.treaty.cdm.domain.dss.agnostic.tracking.ProjectImportAssetLog;
import com.scor.almf.treaty.cdm.repository.dss.agnostic.ProjectImportAssetLogRepository;
import com.scor.almf.treaty.cdm.tools.sequence.MongoDBSequence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by U005342 on 16/07/2018.
 */
@Service
@StepScope
public class ThreadCreatorAndPLTPersistor {

    private static final Logger log = LoggerFactory.getLogger(ThreadCreatorAndPLTPersistor.class);

    @Autowired
    private ProjectImportAssetLogRepository projectImportAssetLogRepository;

    private TransformationPackageNonRMS transformationPackage;

    public Boolean createThreadAndPersistPLTTasklet() {
        log.debug("Start CALCULATE_EPC_EPS_FOR_SOURCE_PLT");
        Date startDate = new Date();

        for (TransformationBundleNonRMS bundle : transformationPackage.getBundles()) {
            // start new step (import progress) : step 6 CREATE_THREAD_AND_PERSIST_PLT for this analysis in loop of many analysis :
            ProjectImportAssetLog projectImportAssetLogA = new ProjectImportAssetLog();
            mongoDBSequence.nextSequenceId(projectImportAssetLogA);
            projectImportAssetLogA.setProjectId(getProjectId());
            projectImportAssetLogA.setStepId(4);
            projectImportAssetLogA.setStepName(StepNonRMS.getStepNameFromStepIdForAnalysis(projectImportAssetLogA.getStepId()));
            projectImportAssetLogA.setStartDate(startDate);
            projectImportAssetLogA.setProjectImportLogId(bundle.getProjectImportLogAnalysisId());
            // --------------------------------------------------------------

            // TODO logic here

            // finis step 6 CREATE_THREAD_AND_PERSIST_PLT for one analysis in loop for of many analysis
            Date endDate = new Date();
            projectImportAssetLogA.setEndDate(endDate);
            projectImportAssetLogRepository.save(projectImportAssetLogA);
            log.info("Finish import progress STEP 6 : CREATE_THREAD_AND_PERSIST_PLT for RRAnalysis : {}", bundle.getRrAnalysis().getId());
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
