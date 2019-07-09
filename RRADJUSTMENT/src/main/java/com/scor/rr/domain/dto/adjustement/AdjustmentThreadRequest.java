package com.scor.rr.domain.dto.adjustement;

public class AdjustmentThreadRequest {

    private int adjustmentThreadId;
    private String threadType;
    private Boolean locked;
    private Integer pltPureId;
    private Integer pltFinalId;

    public int getAdjustmentThreadId() {
        return adjustmentThreadId;
    }

    public void setAdjustmentThreadId(int adjustmentThreadId) {
        this.adjustmentThreadId = adjustmentThreadId;
    }

    public String getThreadType() {
        return threadType;
    }

    public void setThreadType(String threadType) {
        this.threadType = threadType;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public Integer getPltPureId() {
        return pltPureId;
    }

    public void setPltPureId(Integer pltPureId) {
        this.pltPureId = pltPureId;
    }

    public Integer getPltFinalId() {
        return pltFinalId;
    }

    public void setPltFinalId(Integer pltFinalId) {
        this.pltFinalId = pltFinalId;
    }
}
