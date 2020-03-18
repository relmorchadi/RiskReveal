package com.scor.rr.service.batch.writer;

import com.scor.rr.domain.PltHeaderEntity;
import com.scor.rr.domain.StepEntity;
import com.scor.rr.domain.dto.PLTBundle;
import com.scor.rr.domain.enums.StepStatus;
import com.scor.rr.repository.PltHeaderRepository;
import com.scor.rr.service.abstraction.JobManager;
import com.scor.rr.service.state.TransformationBundle;
import com.scor.rr.service.state.TransformationPackage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@StepScope
@Slf4j
public class PLTWriter {

    // TODO : Review its utility with viet
    private static boolean DBG = true;
    @Autowired
    private TransformationPackage transformationPackage;

    @Autowired
    private PltHeaderRepository pltHeaderRepository;

    @Autowired
    @Qualifier("jobManagerImpl")
    private JobManager jobManager;

    @Value("#{jobParameters['taskId']}")
    private String taskId;

    public RepeatStatus writeHeader() {

        StepEntity step = jobManager.createStep(Long.valueOf(taskId), "PLTWriter", 12);
        try {
            log.debug("Starting writeHeader");

            for (TransformationBundle bundle : transformationPackage.getTransformationBundles()) {

                if (bundle.getPltBundles() == null) {
                    log.error("ERROR in RRLT {}, no PLTs found", bundle.getConformedRRLT().getLossDataHeaderId());
                    log.info("Finish import progress STEP 14 : WRITE_PLT_HEADER for analysis: {}", bundle.getSourceResult().getRlImportSelectionId());
                    continue;
                }
                for (PLTBundle pltBundle : bundle.getPltBundles()) {
                    if (!pltBundle.getPltError()) {
                        pltBundle.setHeader(persistHeader(pltBundle.getHeader()));
                    }
                }

                log.info("Finish import progress STEP 14 : WRITE_PLT_HEADER for analysis: {}", bundle.getSourceResult().getRlImportSelectionId());
            }
            log.debug("writeHeader completed");
            jobManager.logStep(step.getStepId(), StepStatus.SUCCEEDED);
            return RepeatStatus.FINISHED;
        } catch (Exception ex) {
            jobManager.onTaskError(Long.valueOf(taskId));
            jobManager.logStep(step.getStepId(), StepStatus.FAILED);
            ex.printStackTrace();
            return RepeatStatus.valueOf("FAILED");
        }
    }

    private PltHeaderEntity persistHeader(PltHeaderEntity pltHeaderEntity) {

        if (pltHeaderEntity.getLossDataFilePath() == null && pltHeaderEntity.getLossDataFileName() != null) {
            throw new IllegalStateException("Write binary files and assign their names to me first");
        }
        // TODO : To review
//        scorPltHeaderEntity.setPltStatus(PLTStatus.ValidFull);
        pltHeaderEntity = pltHeaderRepository.save(pltHeaderEntity);
        if (DBG) log.info("Finish persisting ScorPLTHeader " + pltHeaderEntity.getPltHeaderId());

        return pltHeaderEntity;
    }
}
