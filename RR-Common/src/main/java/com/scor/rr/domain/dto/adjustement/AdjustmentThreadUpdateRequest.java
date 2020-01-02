package com.scor.rr.domain.dto.adjustement;

/**
 * Created by u004602 on 08/10/2019.
 */
public class AdjustmentThreadUpdateRequest {
    private Integer adjustmentThreadId;
    private boolean locked;
    private String lastModifiedBy;

    public AdjustmentThreadUpdateRequest() {
    }

    public AdjustmentThreadUpdateRequest(Integer adjustmentThreadId, Long pltFinalId, boolean locked, String lastModifiedBy) {
        this.adjustmentThreadId = adjustmentThreadId;
        this.locked = locked;
        this.lastModifiedBy = lastModifiedBy;
    }

    public Integer getAdjustmentThreadId() {
        return adjustmentThreadId;
    }

    public void setAdjustmentThreadId(Integer adjustmentThreadId) {
        this.adjustmentThreadId = adjustmentThreadId;
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
