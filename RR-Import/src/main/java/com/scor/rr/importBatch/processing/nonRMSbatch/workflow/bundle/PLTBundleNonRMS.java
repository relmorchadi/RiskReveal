package com.scor.rr.importBatch.processing.nonRMSbatch.workflow.bundle;

import com.scor.rr.domain.entities.plt.ScorPLTHeader;
import com.scor.rr.importBatch.processing.treaty.PLTBundle;
import com.scor.rr.importBatch.processing.treaty.loss.ScorPLTLossDataHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by U005342 on 18/07/2018.
 */
public class PLTBundleNonRMS {

    private static final Logger log= LoggerFactory.getLogger(PLTBundle.class);

    private Boolean pltError = Boolean.FALSE;

    private ScorPLTHeader header;

    private ScorPLTHeader header100k;

    private ScorPLTLossDataHeader lossDataHeader;

    private ScorPLTLossDataHeader lossDataHeader100k;

    public ScorPLTHeader getHeader() {
        return header;
    }

    private String errorMessage;

    public void setHeader(ScorPLTHeader header) {
        this.header = header;
    }

    public synchronized ScorPLTHeader getHeader100k() {
//        if (this.header100k == null) {
//            this.header100k = TransformationUtils.subScorPLTHeader(header);
//        }
//        return header100k;
        return header;
    }

    public ScorPLTLossDataHeader getLossDataHeader() {
        return lossDataHeader;
    }

    public void setLossDataHeader(ScorPLTLossDataHeader lossDataHeader) {
        this.lossDataHeader = lossDataHeader;
    }

    public synchronized ScorPLTLossDataHeader getLossDataHeader100k() {
        if (this.lossDataHeader100k == null) {
//            this.lossDataHeader100k = TransformationUtils.subScorPLTLossDataHeader(lossDataHeader);
            this.lossDataHeader100k = lossDataHeader;
        }
        return this.lossDataHeader100k;
    }

    public Boolean getPltError() {
        pltError = lossDataHeader == null || lossDataHeader.getPltLossPeriods() == null || lossDataHeader.getPltLossPeriods().isEmpty();
        return pltError;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
