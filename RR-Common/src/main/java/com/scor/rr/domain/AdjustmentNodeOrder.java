package com.scor.rr.domain;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Objects;

@Entity
@Table(name = "AdjustmentOrder")
public class AdjustmentNodeOrder {
    private int adjustmentNodeOrderId;
    private Integer adjustmentOrder;
    private AdjustmentThreadEntity adjustmentThread;
    private AdjustmentNode adjustmentNode;

    @Id
    @Column(name = "AdjustmentNodeOrderId", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getAdjustmentNodeOrderId() {
        return adjustmentNodeOrderId;
    }

    public void setAdjustmentNodeOrderId(int adjustmentNodeOrderId) {
        this.adjustmentNodeOrderId = adjustmentNodeOrderId;
    }

    @Basic
    @Column(name = "AdjustmentOrder")
    public Integer getAdjustmentOrder() {
        return adjustmentOrder;
    }

    public void setAdjustmentOrder(Integer adjustmentOrder) {
        this.adjustmentOrder = adjustmentOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdjustmentNodeOrder that = (AdjustmentNodeOrder) o;
        return adjustmentNodeOrderId == that.adjustmentNodeOrderId &&
                Objects.equals(adjustmentOrder, that.adjustmentOrder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(adjustmentNodeOrderId, adjustmentOrder);
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
    public AdjustmentNode getAdjustmentNode() {
        return adjustmentNode;
    }

    public void setAdjustmentNode(AdjustmentNode adjustmentNode) {
        this.adjustmentNode = adjustmentNode;
    }

    @Override
    public String toString() {
        return "nodeId= {"+ adjustmentNode.getAdjustmentNodeId()+"} adjustmentOrder= {" + adjustmentOrder +"}";
    }
}
