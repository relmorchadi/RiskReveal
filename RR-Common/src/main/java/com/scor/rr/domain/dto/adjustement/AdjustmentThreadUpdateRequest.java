package com.scor.rr.domain.dto.adjustement;

/**
 * Created by u004602 on 08/10/2019.
 */
public class AdjustmentThreadUpdateRequest {
    private String threadType;
    private int adjustmentThreadId;
    private int pltFinalId;
    private String lastModifiedBy;

    public AdjustmentThreadUpdateRequest() {
    }

    public AdjustmentThreadUpdateRequest(String threadType, int adjustmentThreadId, int pltFinalId, String lastModifiedBy) {
        this.threadType = threadType;
        this.adjustmentThreadId = adjustmentThreadId;
        this.pltFinalId = pltFinalId;
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getThreadType() {
        return threadType;
    }

    public void setThreadType(String threadType) {
        this.threadType = threadType;
    }

    public int getAdjustmentThreadId() {
        return adjustmentThreadId;
    }

    public void setAdjustmentThreadId(int adjustmentThreadId) {
        this.adjustmentThreadId = adjustmentThreadId;
    }

    public int getPltFinalId() {
        return pltFinalId;
    }

    public void setPltFinalId(int pltFinalId) {
        this.pltFinalId = pltFinalId;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }
}
