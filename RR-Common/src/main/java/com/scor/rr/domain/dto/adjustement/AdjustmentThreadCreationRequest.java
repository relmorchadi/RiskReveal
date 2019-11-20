package com.scor.rr.domain.dto.adjustement;

import java.sql.Timestamp;

public class AdjustmentThreadCreationRequest {
    private Integer pltPureId;
    private String createdBy;
    private boolean generateDefaultThread;

    public AdjustmentThreadCreationRequest() {
    }

    public AdjustmentThreadCreationRequest(Integer pltPureId, String createdBy, boolean generateDefaultThread) {
        this.pltPureId = pltPureId;
        this.createdBy = createdBy;
        this.generateDefaultThread = generateDefaultThread;
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
