package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "AdjustmentThread", schema = "dbo", catalog = "RiskReveal")
public class AdjustmentThreadEntity {
    private int adjustmentThreadId;
    private Integer threadId;
    private String threadType;
    private Boolean locked;
    private Boolean hasB;
    private Boolean hasD;
    private Boolean hasA;
    private Boolean hasC;
    private AdjustmentStructureEntity adjustmentStructureByAdjustmentStuctureId;

    @Id
    @Column(name = "adjustmentThreadId", nullable = false)
    public int getAdjustmentThreadId() {
        return adjustmentThreadId;
    }

    public void setAdjustmentThreadId(int adjustmentThreadId) {
        this.adjustmentThreadId = adjustmentThreadId;
    }

    @Basic
    @Column(name = "threadID", nullable = true)
    public Integer getThreadId() {
        return threadId;
    }

    public void setThreadId(Integer threadId) {
        this.threadId = threadId;
    }

    @Basic
    @Column(name = "threadType", nullable = true, length = 255)
    public String getThreadType() {
        return threadType;
    }

    public void setThreadType(String threadType) {
        this.threadType = threadType;
    }

    @Basic
    @Column(name = "locked", nullable = true)
    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    @Basic
    @Column(name = "hasB", nullable = true)
    public Boolean getHasB() {
        return hasB;
    }

    public void setHasB(Boolean hasB) {
        this.hasB = hasB;
    }

    @Basic
    @Column(name = "hasD", nullable = true)
    public Boolean getHasD() {
        return hasD;
    }

    public void setHasD(Boolean hasD) {
        this.hasD = hasD;
    }

    @Basic
    @Column(name = "hasA", nullable = true)
    public Boolean getHasA() {
        return hasA;
    }

    public void setHasA(Boolean hasA) {
        this.hasA = hasA;
    }

    @Basic
    @Column(name = "hasC", nullable = true)
    public Boolean getHasC() {
        return hasC;
    }

    public void setHasC(Boolean hasC) {
        this.hasC = hasC;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdjustmentThreadEntity that = (AdjustmentThreadEntity) o;
        return adjustmentThreadId == that.adjustmentThreadId &&
                Objects.equals(threadId, that.threadId) &&
                Objects.equals(threadType, that.threadType) &&
                Objects.equals(locked, that.locked) &&
                Objects.equals(hasB, that.hasB) &&
                Objects.equals(hasD, that.hasD) &&
                Objects.equals(hasA, that.hasA) &&
                Objects.equals(hasC, that.hasC);
    }

    @Override
    public int hashCode() {
        return Objects.hash(adjustmentThreadId, threadId, threadType, locked, hasB, hasD, hasA, hasC);
    }

    @ManyToOne
    @JoinColumn(name = "adjustmentStuctureId", referencedColumnName = "adjustmentStuctureId")
    public AdjustmentStructureEntity getAdjustmentStructureByAdjustmentStuctureId() {
        return adjustmentStructureByAdjustmentStuctureId;
    }

    public void setAdjustmentStructureByAdjustmentStuctureId(AdjustmentStructureEntity adjustmentStructureByAdjustmentStuctureId) {
        this.adjustmentStructureByAdjustmentStuctureId = adjustmentStructureByAdjustmentStuctureId;
    }
}
