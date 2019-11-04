//package com.scor.rr.domain;
//
//import javax.persistence.*;
//import java.util.Objects;
//
//@Entity
//@Table(name = "InuringObjectInputNodeInuringPltElement", schema = "dbo", catalog = "RiskReveal")
//public class InuringObjectInputNodeInuringPltElementEntity {
//    private int inuringObjectInputNodeInuringObjectInputNodeId;
//    private Integer outputInuringPltElementsInuringPltElementId;
//    private Integer inputInuringPltElementsInuringPltElementId;
//
//    @Id
//    @Column(name = "InuringObjectInputNodeInuringPltElementId", nullable = false, precision = 0)
//    public int getInuringObjectInputNodeInuringObjectInputNodeId() {
//        return inuringObjectInputNodeInuringObjectInputNodeId;
//    }
//
//    public void setInuringObjectInputNodeInuringObjectInputNodeId(int inuringObjectInputNodeInuringObjectInputNodeId) {
//        this.inuringObjectInputNodeInuringObjectInputNodeId = inuringObjectInputNodeInuringObjectInputNodeId;
//    }
//
//    @Basic
//    @Column(name = "FKOutputInuringPltElementsId", nullable = true, precision = 0)
//    public Integer getOutputInuringPltElementsInuringPltElementId() {
//        return outputInuringPltElementsInuringPltElementId;
//    }
//
//    public void setOutputInuringPltElementsInuringPltElementId(Integer outputInuringPltElementsInuringPltElementId) {
//        this.outputInuringPltElementsInuringPltElementId = outputInuringPltElementsInuringPltElementId;
//    }
//
//    @Basic
//    @Column(name = "FKInputInuringPltElementsId", nullable = true, precision = 0)
//    public Integer getInputInuringPltElementsInuringPltElementId() {
//        return inputInuringPltElementsInuringPltElementId;
//    }
//
//    public void setInputInuringPltElementsInuringPltElementId(Integer inputInuringPltElementsInuringPltElementId) {
//        this.inputInuringPltElementsInuringPltElementId = inputInuringPltElementsInuringPltElementId;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        InuringObjectInputNodeInuringPltElementEntity that = (InuringObjectInputNodeInuringPltElementEntity) o;
//        return inuringObjectInputNodeInuringObjectInputNodeId == that.inuringObjectInputNodeInuringObjectInputNodeId &&
//                Objects.equals(outputInuringPltElementsInuringPltElementId, that.outputInuringPltElementsInuringPltElementId) &&
//                Objects.equals(inputInuringPltElementsInuringPltElementId, that.inputInuringPltElementsInuringPltElementId);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(inuringObjectInputNodeInuringObjectInputNodeId, outputInuringPltElementsInuringPltElementId, inputInuringPltElementsInuringPltElementId);
//    }
//}
