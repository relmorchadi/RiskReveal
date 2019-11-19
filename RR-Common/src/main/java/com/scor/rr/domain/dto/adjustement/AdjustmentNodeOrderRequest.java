package com.scor.rr.domain.dto.adjustement;

public class AdjustmentNodeOrderRequest {
    private Long adjustmentNodeId;
    private Long adjustmentThreadId;
    private int order;

    public AdjustmentNodeOrderRequest(Long adjustmentNodeId, Long adjustmentThreadId, int order) {
        this.adjustmentNodeId = adjustmentNodeId;
        this.adjustmentThreadId = adjustmentThreadId;
        this.order = order;
    }

    public Long getAdjustmentNodeId() {
        return adjustmentNodeId;
    }

    public void setAdjustmentNodeId(Long adjustmentNodeId) {
        this.adjustmentNodeId = adjustmentNodeId;
    }

    public Long getAdjustmentThreadId() {
        return adjustmentThreadId;
    }

    public void setAdjustmentThreadId(Long adjustmentThreadId) {
        this.adjustmentThreadId = adjustmentThreadId;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
