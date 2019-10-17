package com.scor.rr.importBatch.processing.ylt;

import com.scor.rr.domain.entities.plt.ScorPLTHeader;
import com.scor.rr.importBatch.processing.batch.BaseTruncator;
import com.scor.rr.importBatch.processing.domain.PLTData;
import com.scor.rr.importBatch.processing.domain.PLTLoss;
import com.scor.rr.importBatch.processing.domain.PLTPeriod;
import com.scor.rr.importBatch.processing.domain.PLTResult;
import com.scor.rr.importBatch.processing.treaty.PLTBundle;
import com.scor.rr.importBatch.processing.treaty.TransformationBundle;
import com.scor.rr.importBatch.processing.treaty.TransformationPackage;
import com.scor.rr.importBatch.processing.treaty.loss.PLTLossData;
import com.scor.rr.importBatch.processing.treaty.loss.PLTLossPeriod;
import com.scor.rr.importBatch.processing.treaty.loss.ScorPLTLossDataHeader;
import com.scor.rr.repository.plt.ScorPLTHeaderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Iterator;

/**
 * Created by U002629 on 19/02/2015.
 */
public class PLTTruncator extends BaseTruncator implements PLTHandler {

    private static final Logger log= LoggerFactory.getLogger(PLTTruncator.class);

    @Autowired
    private TransformationPackage transformationPackage;

    @Autowired
    private ScorPLTHeaderRepository scorPLTHeaderRepository;

    @Override
    public Boolean handle(){
        log.debug("Starting PLTTruncator");
        for (TransformationBundle bundle : transformationPackage.getBundles()) {
            String region = bundle.getRmsAnalysis().getRegion();
            String peril = bundle.getRmsAnalysis().getPeril();
            String currency = bundle.getRmsAnalysis().getAnalysisCurrency();
//            double threshold = 0;//getThresholdFor(region, peril, currency, "PLT");
            double threshold = getThresholdFor(region, peril, currency, "PLT");

            Double truncatedAAL = 0.0d;
            Integer truncatedEvents = 0;
            Double truncatedSD = 0.0d;

            if (bundle.getEltError()) {
                log.error("Truncation error: RRLT {} has no PLTs", bundle.getConformedRRLT().getRrLossTableId()); // bundle.getConformedELTHeader()
                continue;
            }
            for (PLTBundle pltBundle : bundle.getPltBundles()) {
                ScorPLTHeader pltHeader = pltBundle.getHeader();
//                log.info("Truncating PLTLossDataHeader of PLTHeader {} having ELT {}, targetRAP {}, anlsId {}", pltHeader.getId(), pltHeader.getEltHeader().getId(), pltHeader.getTargetRAP().getTargetRAPCode(), bundle.getRmsAnalysis().getAnalysisId());
                log.info("Truncating PLTLossDataHeader of PLTHeader {} having ELT {}, targetRAP {}, anlsId {}", pltHeader.getScorPLTHeaderId(), pltHeader.getRrLossTableId(), pltHeader.getTargetRAP().getTargetRapCode(), bundle.getRmsAnalysis().getAnalysisId());

                if (pltBundle.getPltError()) {
                    String peqt = pltHeader.getPeqtFile() == null ? null : pltHeader.getPeqtFile().getFileName();
                    String targetRAPId = String.valueOf(pltHeader.getTargetRAP() == null ? null : pltHeader.getTargetRAP().getTargetRAPId());
                    String targetRAPCode = pltHeader.getTargetRAP() == null ? null : pltHeader.getTargetRAP().getTargetRapCode();
                    String sourceRapId = String.valueOf(bundle.getConformedRRLT() == null ? null : bundle.getSourceRapMapping() == null ? null : bundle.getSourceRapMapping().getSourceRapMappingId()); // bundle.getConformedELTHeader().getSourceRAP()
                    String sourceRapCode = bundle.getConformedRRLT() == null ? null : bundle.getSourceRapMapping() == null ? null : bundle.getSourceRapMapping().getSourceRapCode();
                    String analysisId = bundle.getRmsAnalysis() == null ? null : bundle.getRmsAnalysis().getAnalysisId() == null ? null : bundle.getRmsAnalysis().getAnalysisId().toString();
                    String analysisName = bundle.getRmsAnalysis() == null ? null : bundle.getRmsAnalysis().getAnalysisName();

                    String errorMessage = String.format("Error: no PLT events found, PEQT %s, targetRAP %s, targetRAPId %s, sourceRap %s, sourceRapId %s, analysisName %s, analysisId %s",
                            peqt, targetRAPCode, targetRAPId, sourceRapCode, sourceRapId, analysisName, analysisId);
                    bundle.setErrorMessage(errorMessage);

//                    log.error("Truncation error: PLT {} has no loss", pltHeader.getId(), pltHeader.getEltHeader().getId());
                    log.error("Truncation error: PLT {} has no loss", pltHeader.getScorPLTHeaderId(), pltHeader.getRrLossTableId());

                    continue;
                }
                ScorPLTLossDataHeader pltLossDataHeader = pltBundle.getLossDataHeader();
                for (PLTLossPeriod lossPeriod : pltLossDataHeader.getPltLossPeriods()) {
                    Iterator<PLTLossData> iter = lossPeriod.getPltLossDataByPeriods().iterator();
                    while (iter.hasNext()) {
                        PLTLossData pltLossData = iter.next();
                        if (pltLossData.getLoss() < threshold) {
                            iter.remove();
                            truncatedEvents++;

                            truncatedAAL += pltLossData.getLoss();
                        }
                    }
                }

                pltLossDataHeader.setTruncatedAAL(truncatedAAL);
                pltLossDataHeader.setTruncatedEvents(truncatedEvents);
                pltLossDataHeader.setTruncatedSD(truncatedSD);
                pltLossDataHeader.setTruncationCurrency(bundle.getTruncationCurrency());
                pltLossDataHeader.setTruncationThreshold(threshold);

                //persist truncation currency and threshold in ScorPLTHeader
                pltHeader.setTruncationCurrency(bundle.getTruncationCurrency());
                pltHeader.setTruncationThreshold(threshold);
                pltHeader.setTruncationThresholdEur(getThresholdInEur());
            }
        }
        log.debug("PLTTruncator completed");
        return true;
    }

    ////////// FAC ///////////

    private PLTData pltData;

    public PLTData getPltData() {
        return pltData;
    }

    public void setPltData(PLTData pltData) {
        this.pltData = pltData;
    }

    public Boolean handleFAC() {
        for (String regionPeril : pltData.getRegionPerils()) {
            log.info("begin truncating PLT for "+regionPeril);
            PLTLoss loss = pltData.getLossDataForRP(regionPeril);
            double treshold = getThresholdFor(loss.getRegion(), loss.getPeril(), loss.getCurrency(),"PLT");
            Double truncatedAAL = 0.0d;
            Double truncatedSD = 0.0d;
            Integer truncatedEvents = 0;
            for (PLTPeriod next : loss.getPeriods()) {
                Iterator<PLTResult> resultiterator = next.getResults().iterator();
                while (resultiterator.hasNext()) {
                    PLTResult result = resultiterator.next();
                    if (result.getLoss() < treshold) {
                        resultiterator.remove();
                        truncatedEvents++;
                        truncatedAAL+=result.getLoss();
                    }
                }
            }
            loss.setTruncatedAAL(truncatedAAL);
            loss.setTruncatedEvents(truncatedEvents);
            loss.setTruncatedSD(truncatedSD);
            loss.setTruncationCurrency(loss.getCurrency());
            loss.setTruncationThreshold(treshold);
            log.info("finished truncating PLT " + regionPeril);
            addMessage("PLT TRUNC", "PLT truncation OK for "+regionPeril);
        }
        return true;
    }
}
