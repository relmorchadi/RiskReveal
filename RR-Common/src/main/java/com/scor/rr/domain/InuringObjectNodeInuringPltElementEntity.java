package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "InuringObjectNodeInuringPltElement", schema = "dbo", catalog = "RiskReveal")
public class InuringObjectNodeInuringPltElementEntity {
    private int inuringObjectNodeInuringObjectNodeId;
    private Integer outputInuringPltElementsInuringPltElementId;
    private Integer inputInuringPltElementsInuringPltElementId;

    @Id
    @Column(name = "InuringObjectNodeInuringPltElementId", nullable = false, precision = 0)
    public int getInuringObjectNodeInuringObjectNodeId() {
        return inuringObjectNodeInuringObjectNodeId;
    }

    public void setInuringObjectNodeInuringObjectNodeId(int inuringObjectNodeInuringObjectNodeId) {
        this.inuringObjectNodeInuringObjectNodeId = inuringObjectNodeInuringObjectNodeId;
    }

    @Basic
    @Column(name = "outputInuringObjectNodeInuringPltElementId", nullable = true, precision = 0)
    public Integer getOutputInuringPltElementsInuringPltElementId() {
        return outputInuringPltElementsInuringPltElementId;
    }

    public void setOutputInuringPltElementsInuringPltElementId(Integer outputInuringPltElementsInuringPltElementId) {
        this.outputInuringPltElementsInuringPltElementId = outputInuringPltElementsInuringPltElementId;
    }

    @Basic
    @Column(name = "inputInuringObjectNodeInuringPltElementId", nullable = true, precision = 0)
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
        InuringObjectNodeInuringPltElementEntity that = (InuringObjectNodeInuringPltElementEntity) o;
        return inuringObjectNodeInuringObjectNodeId == that.inuringObjectNodeInuringObjectNodeId &&
                Objects.equals(outputInuringPltElementsInuringPltElementId, that.outputInuringPltElementsInuringPltElementId) &&
                Objects.equals(inputInuringPltElementsInuringPltElementId, that.inputInuringPltElementsInuringPltElementId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inuringObjectNodeInuringObjectNodeId, outputInuringPltElementsInuringPltElementId, inputInuringPltElementsInuringPltElementId);
    }
}
