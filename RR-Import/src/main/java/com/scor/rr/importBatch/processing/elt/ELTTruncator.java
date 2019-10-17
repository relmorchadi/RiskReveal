package com.scor.rr.importBatch.processing.elt;

import com.scor.rr.domain.entities.rms.ProjectImportAssetLog;
import com.scor.rr.domain.entities.rms.RMSELTLoss;
import com.scor.rr.importBatch.processing.batch.BaseTruncator;
import com.scor.rr.importBatch.processing.domain.ELTData;
import com.scor.rr.importBatch.processing.domain.ELTLoss;
import com.scor.rr.importBatch.processing.domain.loss.EventLoss;
import com.scor.rr.importBatch.processing.treaty.TransformationBundle;
import com.scor.rr.importBatch.processing.treaty.TransformationPackage;
import com.scor.rr.repository.rms.ProjectImportAssetLogRepository;
import com.scor.rr.repository.tracking.ProjectImportLogRepository;
import com.scor.rr.utils.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Iterator;

/**
 * Created by U002629 on 19/02/2015.
 */
public class ELTTruncator extends BaseTruncator implements ELTHandler {

    private static final Logger log= LoggerFactory.getLogger(ELTTruncator.class);

    @Autowired
    private TransformationPackage transformationPackage;

    @Autowired
    private ProjectImportLogRepository projectImportLogRepository;

    @Autowired
    private ProjectImportAssetLogRepository projectImportAssetLogRepository;

    @Override
    public Boolean batchHandle(){
        log.debug("Starting ELTTruncator");
        Date startDate = new Date();

        for (TransformationBundle bundle : transformationPackage.getBundles()) {
            // start new step (import progress) : step 5 TRUNCATE_ELT for this analysis in loop of many analysis :
            ProjectImportAssetLog projectImportAssetLogA = new ProjectImportAssetLog();
            projectImportAssetLogA.setProjectId(getProjectId());
            projectImportAssetLogA.setStepId(5);
            projectImportAssetLogA.setStepName(Step.getStepNameFromStepIdForAnalysis(projectImportAssetLogA.getStepId()));
            projectImportAssetLogA.setStartDate(startDate);
            projectImportAssetLogA.setProjectImportLogId(bundle.getProjectImportLogAnalysisId());
            // --------------------------------------------------------------

            log.info("truncating ELT data for analysis " + bundle.getRmsAnalysis().getAnalysisId());
            double threshold = 0;//getThresholdFor(bundle.getRmsAnalysis().getRegion(), bundle.getRmsAnalysis().getPeril(), bundle.getRmsAnalysis().getAnalysisCurrency(), "ELT");
            log.info("Threshold found: "+ threshold);
            log.debug("Source ELT size = {}", bundle.getRmsAnalysisELT().getEltLosses().size());
            Double truncatedAAL = 0.0d;
            Double truncatedSD = 0.0d;
            Integer truncatedEvents = 0;
            Iterator<RMSELTLoss> iterator = bundle.getRmsAnalysisELT().getEltLosses().iterator();
            while (iterator.hasNext()) {
                RMSELTLoss next = iterator.next();
                if (next.getLoss() < threshold) {
                    iterator.remove();
                    truncatedEvents++;
                    truncatedAAL += next.getLoss();
                    truncatedSD += next.getStdDevC();
                }
            }

            log.debug("Dest ELT size = {}", bundle.getRmsAnalysisELT().getEltLosses().size());
            bundle.setTruncatedAAL(truncatedAAL);
            bundle.setTruncatedEvents(truncatedEvents);
            bundle.setTruncatedSD(truncatedSD);
            bundle.setTruncationCurrency(bundle.getRmsAnalysis().getAnalysisCurrency());
            bundle.setTruncationThreshold(threshold);

            // finish step 5 TRUNCATE_ELT for one analysis in loop for of many analysis
            Date endDate = new Date();
            projectImportAssetLogA.setEndDate(endDate);
            projectImportAssetLogRepository.save(projectImportAssetLogA);
            log.info("Finish import progress STEP 5 : TRUNCATE_ELT for analysis: {}", bundle.getSourceResult().getSourceResultId());
        }
        log.debug("ELTTruncator completed");
        return true;
    }

    ////////// FAC ////////////

    private ELTData eltData;

    public ELTData getEltData() {
        return eltData;
    }

    public void setEltData(ELTData eltData) {
        this.eltData = eltData;
    }

    public Boolean batchHandleFAC(){
        for (String regionPeril : eltData.getRegionPerils()) {
            log.info("truncating ELT data for "+regionPeril);
            ELTLoss lossData = eltData.getLossDataForRp(regionPeril);
            double treshold = getThresholdFor(lossData.getRegion(), lossData.getPeril(), lossData.getCurrency(), "ELT");
            log.info("Threshold found:  "+treshold);
            Double truncatedAAL = 0.0d;
            Double truncatedSD = 0.0d;
            Integer truncatedEvents = 0;
            Iterator<EventLoss> iterator = lossData.getEventLosses().iterator();
            while (iterator.hasNext()) {
                EventLoss next = iterator.next();
                if(next.getMeanLoss()<treshold) {
                    iterator.remove();
                    truncatedEvents++;
                    truncatedAAL+=next.getMeanLoss();
                    truncatedSD+=next.getStdDevC();
                }
            }
            lossData.setTruncatedAAL(truncatedAAL);
            lossData.setTruncatedEvents(truncatedEvents);
            lossData.setTruncatedSD(truncatedSD);
            lossData.setTruncationCurrency(lossData.getCurrency());
            lossData.setTruncationThreshold(treshold);
            log.info("Finished truncating "+regionPeril);
            addMessage("ELT TRUNCATE", "ELT truncated OK for"+regionPeril);
            log.info("finished truncating ELT data for"+regionPeril);
        }
        return true;
    }
}
