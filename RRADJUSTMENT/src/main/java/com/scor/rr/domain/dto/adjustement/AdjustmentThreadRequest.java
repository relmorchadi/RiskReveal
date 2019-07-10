package com.scor.rr.domain.dto.adjustement;

import java.sql.Timestamp;

public class AdjustmentThreadRequest {

    private int adjustmentThreadId;
    private String threadType;
    private Boolean locked;
    private Integer pltPureId;
    private Integer pltFinalId;
    private String createdBy;
    private Timestamp createdOn;
    private String accessBy;
    private Timestamp accessOn;
    private String lastModifiedBy;
    private Timestamp lastModifiedOn;
    private Timestamp lastGeneratedOn;
    private Timestamp generatedOn;

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

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }

    public String getAccessBy() {
        return accessBy;
    }

    public void setAccessBy(String accessBy) {
        this.accessBy = accessBy;
    }

    public Timestamp getAccessOn() {
        return accessOn;
    }

    public void setAccessOn(Timestamp accessOn) {
        this.accessOn = accessOn;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Timestamp getLastModifiedOn() {
        return lastModifiedOn;
    }

    public void setLastModifiedOn(Timestamp lastModifiedOn) {
        this.lastModifiedOn = lastModifiedOn;
    }

    public Timestamp getLastGeneratedOn() {
        return lastGeneratedOn;
    }

    public void setLastGeneratedOn(Timestamp lastGeneratedOn) {
        this.lastGeneratedOn = lastGeneratedOn;
    }

    public Timestamp getGeneratedOn() {
        return generatedOn;
    }

    public void setGeneratedOn(Timestamp generatedOn) {
        this.generatedOn = generatedOn;
    }
}
