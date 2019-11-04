package com.scor.rr.service.batch.writer;

import com.scor.rr.domain.PLTHeader;
import com.scor.rr.domain.dto.PLTBundle;
import com.scor.rr.repository.PLTHeaderRepository;
import com.scor.rr.service.state.TransformationBundle;
import com.scor.rr.service.state.TransformationPackage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@StepScope
@Slf4j
public class PLTWriter {

    @Autowired
    private TransformationPackage transformationPackage;

    @Autowired
    private PLTHeaderRepository pltHeaderRepository;

    // TODO : Review its utility with viet
    private static boolean DBG = true;

    public RepeatStatus writeHeader(){

            log.debug("Starting writeHeader");

            for (TransformationBundle bundle : transformationPackage.getTransformationBundles()) {

                if (bundle.getPltBundles() == null) {
                    log.error("ERROR in RRLT {}, no PLTs found", bundle.getConformedRRLT().getLossDataHeaderId());
                    log.info("Finish import progress STEP 14 : WRITE_PLT_HEADER for analysis: {}", bundle.getSourceResult().getRlSourceResultId());
                    continue;
                }
                for (PLTBundle pltBundle : bundle.getPltBundles()) {
                    if (!pltBundle.getPltError()) {
                        persistHeader(pltBundle.getHeader());
                    }
                }

                log.info("Finish import progress STEP 14 : WRITE_PLT_HEADER for analysis: {}", bundle.getSourceResult().getRlSourceResultId());
            }
            log.debug("writeHeader completed");
            return RepeatStatus.FINISHED;
    }

    private void persistHeader(PLTHeader scorPLTHeader) {

        if (scorPLTHeader.getPltLossDataFilePath() == null && scorPLTHeader.getPltLossDataFileName() != null) {
            throw new IllegalStateException("Write binary files and assign their names to me first");
        }
        // TODO : To review
        //scorPLTHeader.setPltStatus(PLTStatus.ValidFull);
        pltHeaderRepository.save(scorPLTHeader);
        if (DBG) log.info("Finish persisting ScorPLTHeader " + scorPLTHeader.getPltHeaderId());
    }
}
