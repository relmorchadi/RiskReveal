package com.scor.rr.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Objects;

@Entity
@Table(name = "EventBasedAdjustmentParameter")
public class EventBasedAdjustmentParameter {
    private int adjustmentParameterId;
    private Integer entity;
    private String inputFilePath;
    private String inputFileName;
    private AdjustmentNode adjustmentNode;

    public EventBasedAdjustmentParameter() {
    }

    public EventBasedAdjustmentParameter(String inputFilePath, String inputFileName, AdjustmentNode adjustmentNode) {
        this.inputFilePath = inputFilePath;
        this.inputFileName = inputFileName;
        this.adjustmentNode = adjustmentNode;
    }

    @Id
    @Column(name = "AdjustmentParameterId", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getAdjustmentParameterId() {
        return adjustmentParameterId;
    }

    public void setAdjustmentParameterId(int adjustmentParameterId) {
        this.adjustmentParameterId = adjustmentParameterId;
    }

    @Column(name = "Entity")
    public Integer getEntity() {
        return entity;
    }

    public void setEntity(Integer entity) {
        this.entity = entity;
    }

    @Basic
    @Column(name = "InputFilePath", nullable = true, length = 500)
    public String getInputFilePath() {
        return inputFilePath;
    }

    public void setInputFilePath(String inputFilePath) {
        this.inputFilePath = inputFilePath;
    }

    @Basic
    @Column(name = "InputFileName", nullable = true, length = 500)
    public String getInputFileName() {
        return inputFileName;
    }

    public void setInputFileName(String inputFileName) {
        this.inputFileName = inputFileName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventBasedAdjustmentParameter that = (EventBasedAdjustmentParameter) o;
        return adjustmentParameterId == that.adjustmentParameterId &&
                Objects.equals(inputFilePath, that.inputFilePath) &&
                Objects.equals(inputFileName, that.inputFileName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(adjustmentParameterId, inputFilePath, inputFileName);
    }

    @ManyToOne
    @JoinColumn(name = "AdjustmentNodeId", referencedColumnName = "AdjustmentNodeId")
    @JsonBackReference
    public AdjustmentNode getAdjustmentNode() {
        return adjustmentNode;
    }

    public void setAdjustmentNode(AdjustmentNode adjustmentNode) {
        this.adjustmentNode = adjustmentNode;
    }
}
