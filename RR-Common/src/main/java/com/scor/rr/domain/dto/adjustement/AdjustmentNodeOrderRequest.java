package com.scor.rr.domain.dto.adjustement;

public class AdjustmentNodeOrderRequest {
    private Integer adjustmentNodeId;
    private Integer adjustmentThreadId;
    private int order;

    public AdjustmentNodeOrderRequest(Integer adjustmentNodeId, Integer adjustmentThreadId, int order) {
        this.adjustmentNodeId = adjustmentNodeId;
        this.adjustmentThreadId = adjustmentThreadId;
        this.order = order;
    }

    public Integer getAdjustmentNodeId() {
        return adjustmentNodeId;
    }

    public void setAdjustmentNodeId(Integer adjustmentNodeId) {
        this.adjustmentNodeId = adjustmentNodeId;
    }

    public Integer getAdjustmentThreadId() {
        return adjustmentThreadId;
    }

    public void setAdjustmentThreadId(Integer adjustmentThreadId) {
        this.adjustmentThreadId = adjustmentThreadId;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
