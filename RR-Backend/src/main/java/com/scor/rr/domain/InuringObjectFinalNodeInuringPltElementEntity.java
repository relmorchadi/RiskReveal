package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "InuringObjectFinalNode_InuringPltElement", schema = "dbo", catalog = "RiskReveal")
public class InuringObjectFinalNodeInuringPltElementEntity {
    private int inuringObjectFinalNodeInuringObjectFinalNodeId;
    private Integer outputInuringPltElementsInuringPltElementId;
    private Integer inputInuringPltElementsInuringPltElementId;

    @Id
    @Column(name = "InuringObjectFinalNode_InuringObjectFinalNode_Id", nullable = false, precision = 0)
    public int getInuringObjectFinalNodeInuringObjectFinalNodeId() {
        return inuringObjectFinalNodeInuringObjectFinalNodeId;
    }

    public void setInuringObjectFinalNodeInuringObjectFinalNodeId(int inuringObjectFinalNodeInuringObjectFinalNodeId) {
        this.inuringObjectFinalNodeInuringObjectFinalNodeId = inuringObjectFinalNodeInuringObjectFinalNodeId;
    }

    @Basic
    @Column(name = "outputInuringPltElements_InuringPltElementId", nullable = true, precision = 0)
    public Integer getOutputInuringPltElementsInuringPltElementId() {
        return outputInuringPltElementsInuringPltElementId;
    }

    public void setOutputInuringPltElementsInuringPltElementId(Integer outputInuringPltElementsInuringPltElementId) {
        this.outputInuringPltElementsInuringPltElementId = outputInuringPltElementsInuringPltElementId;
    }

    @Basic
    @Column(name = "inputInuringPltElements_InuringPltElementId", nullable = true, precision = 0)
    public Integer getInputInuringPltElementsInuringPltElementId() {
        return inputInuringPltElementsInuringPltElementId;
    }

    public void setInputInuringPltElementsInuringPltElementId(Integer inputInuringPltElementsInuringPltElementId) {
        this.inputInuringPltElementsInuringPltElementId = inputInuringPltElementsInuringPltElementId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InuringObjectFinalNodeInuringPltElementEntity that = (InuringObjectFinalNodeInuringPltElementEntity) o;
        return inuringObjectFinalNodeInuringObjectFinalNodeId == that.inuringObjectFinalNodeInuringObjectFinalNodeId &&
                Objects.equals(outputInuringPltElementsInuringPltElementId, that.outputInuringPltElementsInuringPltElementId) &&
                Objects.equals(inputInuringPltElementsInuringPltElementId, that.inputInuringPltElementsInuringPltElementId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inuringObjectFinalNodeInuringObjectFinalNodeId, outputInuringPltElementsInuringPltElementId, inputInuringPltElementsInuringPltElementId);
    }
}
