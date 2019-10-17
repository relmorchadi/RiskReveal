package com.scor.rr.domain.dto.adjustement;

public class AdjustmentNodeProcessingRequest {
    private int adjustmentNodeProcessingId;
    private Integer scorPltHeaderIdPure;
    private Integer adjustmentNodeId;

    public AdjustmentNodeProcessingRequest(Integer scorPltHeaderIdPure, Integer adjustmentNodeId) {
        this.scorPltHeaderIdPure = scorPltHeaderIdPure;
        this.adjustmentNodeId = adjustmentNodeId;
    }

    public AdjustmentNodeProcessingRequest() {
    }

    public int getAdjustmentNodeProcessingId() {
        return adjustmentNodeProcessingId;
    }

    public void setAdjustmentNodeProcessingId(int adjustmentNodeProcessingId) {
        this.adjustmentNodeProcessingId = adjustmentNodeProcessingId;
    }

    public Integer getScorPltHeaderIdPure() {
        return scorPltHeaderIdPure;
    }

    public void setScorPltHeaderIdPure(Integer scorPltHeaderIdPure) {
        this.scorPltHeaderIdPure = scorPltHeaderIdPure;
    }

    public Integer getAdjustmentNodeId() {
        return adjustmentNodeId;
    }

    public void setAdjustmentNodeId(Integer adjustmentNodeId) {
        this.adjustmentNodeId = adjustmentNodeId;
    }
}
