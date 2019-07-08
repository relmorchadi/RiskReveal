package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "AdjustmentThread", schema = "dbo", catalog = "RiskReveal")
public class AdjustmentThreadEntity {
    private int adjustmentThreadId;
    private String threadType;
    private Boolean locked;
    private ScorPltHeaderEntity scorPltHeaderByPurePltId;
    private ScorPltHeaderEntity scorPltHeaderByThreadPltId;

    @Id
    @Column(name = "adjustmentThreadId", nullable = false)
    public int getAdjustmentThreadId() {
        return adjustmentThreadId;
    }

    public void setAdjustmentThreadId(int adjustmentThreadId) {
        this.adjustmentThreadId = adjustmentThreadId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdjustmentThreadEntity that = (AdjustmentThreadEntity) o;
        return adjustmentThreadId == that.adjustmentThreadId &&
                Objects.equals(threadType, that.threadType) &&
                Objects.equals(locked, that.locked);
    }

    @Override
    public int hashCode() {
        return Objects.hash(adjustmentThreadId, threadType, locked);
    }

    @ManyToOne
    @JoinColumn(name = "purePltId", referencedColumnName = "scorPLTHeaderId")
    public ScorPltHeaderEntity getScorPltHeaderByPurePltId() {
        return scorPltHeaderByPurePltId;
    }

    public void setScorPltHeaderByPurePltId(ScorPltHeaderEntity scorPltHeaderByPurePltId) {
        this.scorPltHeaderByPurePltId = scorPltHeaderByPurePltId;
    }

    @ManyToOne
    @JoinColumn(name = "threadPltId", referencedColumnName = "scorPLTHeaderId")
    public ScorPltHeaderEntity getScorPltHeaderByThreadPltId() {
        return scorPltHeaderByThreadPltId;
    }

    public void setScorPltHeaderByThreadPltId(ScorPltHeaderEntity scorPltHeaderByThreadPltId) {
        this.scorPltHeaderByThreadPltId = scorPltHeaderByThreadPltId;
    }
}
