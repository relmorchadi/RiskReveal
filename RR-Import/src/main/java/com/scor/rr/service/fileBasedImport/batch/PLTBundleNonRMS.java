package com.scor.rr.service.fileBasedImport.batch;

import com.scor.rr.domain.PltHeaderEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PLTBundleNonRMS {
    private static final Logger log= LoggerFactory.getLogger(PLTBundleNonRMS.class);

    private Boolean pltError = Boolean.FALSE;

    private PltHeaderEntity header;

    private PltHeaderEntity header100k;

    private ScorPLTLossDataHeader lossDataHeader;

    private ScorPLTLossDataHeader lossDataHeader100k;

    private Long summaryStatisticHeaderId;

    public PltHeaderEntity getHeader() {
        return header;
    }

    private String errorMessage;

    public void setHeader(PltHeaderEntity header) {
        this.header = header;
    }

    public synchronized PltHeaderEntity getHeader100k() {
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

    public Long getSummaryStatisticHeaderId() {
        return summaryStatisticHeaderId;
    }

    public void setSummaryStatisticHeaderId(Long summaryStatisticHeaderId) {
        this.summaryStatisticHeaderId = summaryStatisticHeaderId;
    }
}
