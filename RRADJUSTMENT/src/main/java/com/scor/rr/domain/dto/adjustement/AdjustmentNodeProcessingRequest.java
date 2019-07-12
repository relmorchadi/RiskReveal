package com.scor.rr.domain.dto.adjustement;

public class AdjustmentNodeProcessingRequest {
    private Integer scorPltHeaderIdPure;
    private Integer scorPltHeaderIdAdjusted;
    private long adjustmentNodeId;


    public Integer getScorPltHeaderIdPure() {
        return scorPltHeaderIdPure;
    }

    public void setScorPltHeaderIdPure(Integer scorPltHeaderIdPure) {
        this.scorPltHeaderIdPure = scorPltHeaderIdPure;
    }

    public Integer getScorPltHeaderIdAdjusted() {
        return scorPltHeaderIdAdjusted;
    }

    public void setScorPltHeaderIdAdjusted(Integer scorPltHeaderIdAdjusted) {
        this.scorPltHeaderIdAdjusted = scorPltHeaderIdAdjusted;
    }

    public long getAdjustmentNodeId() {
        return adjustmentNodeId;
    }

    public void setAdjustmentNodeId(long adjustmentNodeId) {
        this.adjustmentNodeId = adjustmentNodeId;
    }
}
