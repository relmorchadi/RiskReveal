package com.scor.rr.domain.dto.adjustement;

public class AdjustmentNodeOrderRequest {
    private int adjustmentNodeId;
    private int adjustmentThreadId;
    private int order;

    public AdjustmentNodeOrderRequest(int adjustmentNodeId, int adjustmentThreadId, int order) {
        this.adjustmentNodeId = adjustmentNodeId;
        this.adjustmentThreadId = adjustmentThreadId;
        this.order = order;
    }

    public int getAdjustmentNodeId() {
        return adjustmentNodeId;
    }

    public void setAdjustmentNodeId(int adjustmentNodeId) {
        this.adjustmentNodeId = adjustmentNodeId;
    }

    public int getAdjustmentThreadId() {
        return adjustmentThreadId;
    }

    public void setAdjustmentThreadId(int adjustmentThreadId) {
        this.adjustmentThreadId = adjustmentThreadId;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
