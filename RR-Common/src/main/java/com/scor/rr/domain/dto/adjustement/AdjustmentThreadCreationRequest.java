package com.scor.rr.domain.dto.adjustement;

import java.sql.Timestamp;

public class AdjustmentThreadCreationRequest {
    private String threadType;
    private Integer pltPureId;
    private String createdBy;
    private boolean generateDefaultThread;

    public AdjustmentThreadCreationRequest(String threadType, Integer pltPureId, String createdBy, boolean generateDefaultThread) {
        this.threadType = threadType;
        this.pltPureId = pltPureId;
        this.createdBy = createdBy;
        this.generateDefaultThread = generateDefaultThread;
    }

    public String getThreadType() {
        return threadType;
    }

    public void setThreadType(String threadType) {
        this.threadType = threadType;
    }

    public Integer getPltPureId() {
        return pltPureId;
    }

    public void setPltPureId(Integer pltPureId) {
        this.pltPureId = pltPureId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public boolean isGenerateDefaultThread() {
        return generateDefaultThread;
    }

    public void setGenerateDefaultThread(boolean generateDefaultThread) {
        this.generateDefaultThread = generateDefaultThread;
    }
}
