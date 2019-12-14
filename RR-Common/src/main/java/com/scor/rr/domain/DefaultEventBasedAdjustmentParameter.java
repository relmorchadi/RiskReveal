package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "DefaultEventBasedAdjustmentParameter", schema = "dbo", catalog = "RiskReveal")
public class DefaultEventBasedAdjustmentParameter {
    private int defaultAdjustmentParameterId;
    private String inputFilePath;
    private String inputFileName;
    private DefaultAdjustmentNode defaultAdjustmentNode;

    public DefaultEventBasedAdjustmentParameter() {
    }

    public DefaultEventBasedAdjustmentParameter(String inputFilePath, String inputFileName, DefaultAdjustmentNode defaultAdjustmentNode) {
        this.inputFilePath = inputFilePath;
        this.inputFileName = inputFileName;
        this.defaultAdjustmentNode = defaultAdjustmentNode;
    }

    @Id
    @Column(name = "DefaultAdjustmentParameterId", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getDefaultAdjustmentParameterId() {
        return defaultAdjustmentParameterId;
    }

    public void setDefaultAdjustmentParameterId(int defaultAdjustmentParameterId) {
        this.defaultAdjustmentParameterId = defaultAdjustmentParameterId;
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
        DefaultEventBasedAdjustmentParameter that = (DefaultEventBasedAdjustmentParameter) o;
        return defaultAdjustmentParameterId == that.defaultAdjustmentParameterId &&
                Objects.equals(inputFilePath, that.inputFilePath) &&
                Objects.equals(inputFileName, that.inputFileName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(defaultAdjustmentParameterId, inputFilePath, inputFileName);
    }

    @ManyToOne
    @JoinColumn(name = "DefaultAdjustmentNodeId", referencedColumnName = "DefaultAdjustmentNodeId")
    public DefaultAdjustmentNode getDefaultAdjustmentNode() {
        return defaultAdjustmentNode;
    }

    public void setDefaultAdjustmentNode(DefaultAdjustmentNode defaultAdjustmentNode) {
        this.defaultAdjustmentNode = defaultAdjustmentNode;
    }
}