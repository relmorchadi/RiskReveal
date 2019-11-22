package com.scor.rr.domain.dto.adjustement;

/**
 * Created by U004602 on 21/11/2019.
 */
public class AdjustmentThreadBranchingRequest {
    private int adjustmentThreadId;
    private boolean generateDefaultThread;
    private String createdBy;

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public int getAdjustmentThreadId() {
        return adjustmentThreadId;
    }

    public void setAdjustmentThreadId(int adjustmentThreadId) {
        this.adjustmentThreadId = adjustmentThreadId;
    }

    public boolean isGenerateDefaultThread() {
        return generateDefaultThread;
    }

    public void setGenerateDefaultThread(boolean generateDefaultThread) {
        this.generateDefaultThread = generateDefaultThread;
    }
}
