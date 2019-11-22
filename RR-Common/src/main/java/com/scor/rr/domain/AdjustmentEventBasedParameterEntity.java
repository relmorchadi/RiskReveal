package com.scor.rr.domain;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Objects;

@Entity
@Table(name = "AdjustmentEventBasedParameter", schema = "dbo", catalog = "RiskReveal")
public class AdjustmentEventBasedParameterEntity {
    private int adjustmentEventBasedParameterId;
    private String inputFilePath;
    private String inputFileName;
    private AdjustmentNode adjustmentNode;

    public AdjustmentEventBasedParameterEntity() {
    }

    public AdjustmentEventBasedParameterEntity(String inputFilePath, String inputFileName, AdjustmentNode adjustmentNode) {
        this.inputFilePath = inputFilePath;
        this.inputFileName = inputFileName;
        this.adjustmentNode = adjustmentNode;
    }

    @Id
    @Column(name = "AdjustmentEventBasedParameterId", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getAdjustmentEventBasedParameterId() {
        return adjustmentEventBasedParameterId;
    }

    public void setAdjustmentEventBasedParameterId(int adjustmentEventBasedParameterId) {
        this.adjustmentEventBasedParameterId = adjustmentEventBasedParameterId;
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
        AdjustmentEventBasedParameterEntity that = (AdjustmentEventBasedParameterEntity) o;
        return adjustmentEventBasedParameterId == that.adjustmentEventBasedParameterId &&
                Objects.equals(inputFilePath, that.inputFilePath) &&
                Objects.equals(inputFileName, that.inputFileName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(adjustmentEventBasedParameterId, inputFilePath, inputFileName);
    }

    @ManyToOne
    @JoinColumn(name = "AdjustmentNodeId", referencedColumnName = "AdjustmentNodeId")
    public AdjustmentNode getAdjustmentNode() {
        return adjustmentNode;
    }

    public void setAdjustmentNode(AdjustmentNode adjustmentNode) {
        this.adjustmentNode = adjustmentNode;
    }
}
