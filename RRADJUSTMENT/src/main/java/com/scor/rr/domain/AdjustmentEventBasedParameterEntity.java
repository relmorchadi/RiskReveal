package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "AdjustmentEventBasedParameter", schema = "dbo", catalog = "RiskReveal")
public class AdjustmentEventBasedParameterEntity {
    private int adjustmentEventBasedParameterId;
    private String inputFilePath;
    private String inputFileName;
    private AdjustmentNodeEntity adjustmentNodeByFkAdjustmentNodeId;

    public AdjustmentEventBasedParameterEntity() {
    }

    public AdjustmentEventBasedParameterEntity(String inputFilePath, String inputFileName, AdjustmentNodeEntity adjustmentNodeByFkAdjustmentNodeId) {
        this.inputFilePath = inputFilePath;
        this.inputFileName = inputFileName;
        this.adjustmentNodeByFkAdjustmentNodeId = adjustmentNodeByFkAdjustmentNodeId;
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
    @Column(name = "InputFilePath", length = 500)
    public String getInputFilePath() {
        return inputFilePath;
    }

    public void setInputFilePath(String inputFilePath) {
        this.inputFilePath = inputFilePath;
    }

    @Basic
    @Column(name = "InputFileName", length = 500)
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
    @JoinColumn(name = "FKAdjustmentNodeId", referencedColumnName = "AdjustmentNodeId")
    public AdjustmentNodeEntity getAdjustmentNodeByFkAdjustmentNodeId() {
        return adjustmentNodeByFkAdjustmentNodeId;
    }

    public void setAdjustmentNodeByFkAdjustmentNodeId(AdjustmentNodeEntity adjustmentNodeByFkAdjustmentNodeId) {
        this.adjustmentNodeByFkAdjustmentNodeId = adjustmentNodeByFkAdjustmentNodeId;
    }
}
