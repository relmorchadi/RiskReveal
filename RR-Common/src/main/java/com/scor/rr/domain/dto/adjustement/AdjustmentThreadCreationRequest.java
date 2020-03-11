package com.scor.rr.domain.dto.adjustement;

public class AdjustmentThreadCreationRequest {
    private Long pltPureId;
    private String createdBy;
    private boolean generateDefaultThread;

    public AdjustmentThreadCreationRequest() {
    }

    public AdjustmentThreadCreationRequest(Long pltPureId, String createdBy, boolean generateDefaultThread) {
        this.pltPureId = pltPureId;
        this.createdBy = createdBy;
        this.generateDefaultThread = generateDefaultThread;
    }

    public Long getPltPureId() {
        return pltPureId;
    }

    public void setPltPureId(Long pltPureId) {
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
