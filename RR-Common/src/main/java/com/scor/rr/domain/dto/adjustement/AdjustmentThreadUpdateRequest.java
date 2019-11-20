package com.scor.rr.domain.dto.adjustement;

/**
 * Created by u004602 on 08/10/2019.
 */
public class AdjustmentThreadUpdateRequest {
    private int adjustmentThreadId;
    private int pltFinalId;
    private boolean locked;
    private String lastModifiedBy;

    public AdjustmentThreadUpdateRequest() {
    }

    public AdjustmentThreadUpdateRequest(int adjustmentThreadId, int pltFinalId, boolean locked, String lastModifiedBy) {
        this.adjustmentThreadId = adjustmentThreadId;
        this.pltFinalId = pltFinalId;
        this.locked = locked;
        this.lastModifiedBy = lastModifiedBy;
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

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }
}
