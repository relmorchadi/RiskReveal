package com.scor.rr.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "AdjustmentProcessingAudit", schema = "dbo", catalog = "RiskReveal")
public class AdjustmentProcessingAuditEntity {
    private int id;
    private Integer adjustmentNodeId;
    private String adjustmentRecap;
    private Timestamp startedTime;
    private Timestamp endedTime;
    private Integer inputPltId;
    private Integer adjustedPltId;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "adjustmentNodeId", nullable = true)
    public Integer getAdjustmentNodeId() {
        return adjustmentNodeId;
    }

    public void setAdjustmentNodeId(Integer adjustmentNodeId) {
        this.adjustmentNodeId = adjustmentNodeId;
    }

    @Basic
    @Column(name = "adjustmentRecap", nullable = true, length = 255)
    public String getAdjustmentRecap() {
        return adjustmentRecap;
    }

    public void setAdjustmentRecap(String adjustmentRecap) {
        this.adjustmentRecap = adjustmentRecap;
    }

    @Basic
    @Column(name = "startedTime", nullable = true)
    public Timestamp getStartedTime() {
        return startedTime;
    }

    public void setStartedTime(Timestamp startedTime) {
        this.startedTime = startedTime;
    }

    @Basic
    @Column(name = "endedTime", nullable = true)
    public Timestamp getEndedTime() {
        return endedTime;
    }

    public void setEndedTime(Timestamp endedTime) {
        this.endedTime = endedTime;
    }

    @Basic
    @Column(name = "inputPltId", nullable = true)
    public Integer getInputPltId() {
        return inputPltId;
    }

    public void setInputPltId(Integer inputPltId) {
        this.inputPltId = inputPltId;
    }

    @Basic
    @Column(name = "adjustedPltId", nullable = true)
    public Integer getAdjustedPltId() {
        return adjustedPltId;
    }

    public void setAdjustedPltId(Integer adjustedPltId) {
        this.adjustedPltId = adjustedPltId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdjustmentProcessingAuditEntity that = (AdjustmentProcessingAuditEntity) o;
        return id == that.id &&
                Objects.equals(adjustmentNodeId, that.adjustmentNodeId) &&
                Objects.equals(adjustmentRecap, that.adjustmentRecap) &&
                Objects.equals(startedTime, that.startedTime) &&
                Objects.equals(endedTime, that.endedTime) &&
                Objects.equals(inputPltId, that.inputPltId) &&
                Objects.equals(adjustedPltId, that.adjustedPltId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, adjustmentNodeId, adjustmentRecap, startedTime, endedTime, inputPltId, adjustedPltId);
    }
}
