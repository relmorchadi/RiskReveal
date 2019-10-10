package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "AdjustmentOrder", schema = "dbo", catalog = "RiskReveal")
public class AdjustmentNodeOrderEntity {
    private int adjustmentNodeOrderId;
    private Integer orderNode;
    private AdjustmentThreadEntity adjustmentThread;
    private AdjustmentNodeEntity adjustmentNode;

    @Id
    @Column(name = "adjustmentNodeOrderId", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getAdjustmentNodeOrderId() {
        return adjustmentNodeOrderId;
    }

    public void setAdjustmentNodeOrderId(int adjustmentNodeOrderId) {
        this.adjustmentNodeOrderId = adjustmentNodeOrderId;
    }

    @Basic
    @Column(name = "AdjustmentOrder")
    public Integer getOrderNode() {
        return orderNode;
    }

    public void setOrderNode(Integer orderNode) {
        this.orderNode = orderNode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdjustmentNodeOrderEntity that = (AdjustmentNodeOrderEntity) o;
        return adjustmentNodeOrderId == that.adjustmentNodeOrderId &&
                Objects.equals(orderNode, that.orderNode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(adjustmentNodeOrderId, orderNode);
    }

    @ManyToOne
    @JoinColumn(name = "adjustmentThreadId", referencedColumnName = "AdjustmentThreadId")
    public AdjustmentThreadEntity getAdjustmentThread() {
        return adjustmentThread;
    }

    public void setAdjustmentThread(AdjustmentThreadEntity adjustmentThread) {
        this.adjustmentThread = adjustmentThread;
    }

    @ManyToOne
    @JoinColumn(name = "adjustmentNodeId", referencedColumnName = "AdjustmentNodeId")
    public AdjustmentNodeEntity getAdjustmentNode() {
        return adjustmentNode;
    }

    public void setAdjustmentNode(AdjustmentNodeEntity adjustmentNode) {
        this.adjustmentNode = adjustmentNode;
    }

    @Override
    public String toString() {
        return "nodeId= {"+ adjustmentNode.getAdjustmentNodeId()+"} orderNode= {" + orderNode+"}";
    }
}
