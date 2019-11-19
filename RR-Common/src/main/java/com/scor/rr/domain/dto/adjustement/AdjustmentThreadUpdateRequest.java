package com.scor.rr.domain.dto.adjustement;

/**
 * Created by u004602 on 08/10/2019.
 */
public class AdjustmentThreadUpdateRequest {
    private Long adjustmentThreadId;
    private Long pltFinalId;
    private boolean locked;
    private String lastModifiedBy;

    public AdjustmentThreadUpdateRequest() {
    }

    public AdjustmentThreadUpdateRequest(Long adjustmentThreadId, Long pltFinalId, boolean locked, String lastModifiedBy) {
        this.adjustmentThreadId = adjustmentThreadId;
        this.pltFinalId = pltFinalId;
        this.locked = locked;
        this.lastModifiedBy = lastModifiedBy;
    }

    public Long getAdjustmentThreadId() {
        return adjustmentThreadId;
    }

    public void setAdjustmentThreadId(Long adjustmentThreadId) {
        this.adjustmentThreadId = adjustmentThreadId;
    }

    public Long getPltFinalId() {
        return pltFinalId;
    }

    public void setPltFinalId(Long pltFinalId) {
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
