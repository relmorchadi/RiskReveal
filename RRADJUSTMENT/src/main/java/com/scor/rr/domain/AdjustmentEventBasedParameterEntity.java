package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "AdjustmentEventBasedParameter", schema = "dbo", catalog = "RiskReveal")
public class AdjustmentEventBasedParameterEntity {
    private int idAdjustmentParameter;
    private String inputFilePath;
    private String inputFileName;
    private AdjustmentNodeEntity adjustmentNodeByIdAdjustmentNode;

    public AdjustmentEventBasedParameterEntity() {
    }

    public AdjustmentEventBasedParameterEntity(String inputFilePath, String inputFileName, AdjustmentNodeEntity adjustmentNodeByIdAdjustmentNode) {
        this.inputFilePath = inputFilePath;
        this.inputFileName = inputFileName;
        this.adjustmentNodeByIdAdjustmentNode = adjustmentNodeByIdAdjustmentNode;
    }

    @Id
    @Column(name = "id_adjustment_parameter", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public int getIdAdjustmentParameter() {
        return idAdjustmentParameter;
    }

    public void setIdAdjustmentParameter(int idAdjustmentParameter) {
        this.idAdjustmentParameter = idAdjustmentParameter;
    }

    @Basic
    @Column(name = "input_file_path", nullable = true, length = 500)
    public String getInputFilePath() {
        return inputFilePath;
    }

    public void setInputFilePath(String inputFilePath) {
        this.inputFilePath = inputFilePath;
    }

    @Basic
    @Column(name = "input_file_name", nullable = true, length = 500)
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
        return idAdjustmentParameter == that.idAdjustmentParameter &&
                Objects.equals(inputFilePath, that.inputFilePath) &&
                Objects.equals(inputFileName, that.inputFileName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAdjustmentParameter, inputFilePath, inputFileName);
    }

    @ManyToOne
    @JoinColumn(name = "id_adjustment_node", referencedColumnName = "id_adjustment_node")
    public AdjustmentNodeEntity getAdjustmentNodeByIdAdjustmentNode() {
        return adjustmentNodeByIdAdjustmentNode;
    }

    public void setAdjustmentNodeByIdAdjustmentNode(AdjustmentNodeEntity adjustmentNodeByIdAdjustmentNode) {
        this.adjustmentNodeByIdAdjustmentNode = adjustmentNodeByIdAdjustmentNode;
    }
}
