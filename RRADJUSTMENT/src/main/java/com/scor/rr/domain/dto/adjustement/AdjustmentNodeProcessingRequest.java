package com.scor.rr.domain.dto.adjustement;

public class AdjustmentNodeProcessingRequest {
    private Integer scorPltHeaderIdPure;
    private Integer adjustmentNodeId;


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
