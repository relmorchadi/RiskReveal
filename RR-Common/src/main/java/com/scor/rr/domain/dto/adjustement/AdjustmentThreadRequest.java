package com.scor.rr.domain.dto.adjustement;

import java.sql.Timestamp;

public class AdjustmentThreadRequest {

    private int adjustmentThreadId;
    private String threadType;
    private Boolean locked;
    private Integer pltPureId;
    private Integer pltFinalId;
    private String createdBy;
    private String accessBy;
    private String lastModifiedBy;

    public AdjustmentThreadRequest() {
    }

    public AdjustmentThreadRequest(int adjustmentThreadId, Integer pltFinalId, String lastModifiedBy) {
        this.adjustmentThreadId = adjustmentThreadId;
        this.pltFinalId = pltFinalId;
        this.lastModifiedBy = lastModifiedBy;
    }

    public AdjustmentThreadRequest(String threadType, Boolean locked, Integer pltPureId, Integer pltFinalId, String createdBy, String accessBy, String lastModifiedBy) {
        this.threadType = threadType;
        this.locked = locked;
        this.pltPureId = pltPureId;
        this.pltFinalId = pltFinalId;
        this.createdBy = createdBy;
        this.accessBy = accessBy;
        this.lastModifiedBy = lastModifiedBy;
    }

    public void setAdjustmentThreadId(int adjustmentThreadId) {
        this.adjustmentThreadId = adjustmentThreadId;
    }

    public int getAdjustmentThreadId() {
        return adjustmentThreadId;
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

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getAccessBy() {
        return accessBy;
    }

    public void setAccessBy(String accessBy) {
        this.accessBy = accessBy;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

}
