//package com.scor.rr.domain;
//
//import javax.persistence.*;
//import java.util.Objects;
//
//@Entity
//@Table(name = "InuringObjectContractNodeInuringPltElement", schema = "dbo", catalog = "RiskReveal")
//public class InuringObjectContractNodeInuringPltElementEntity {
//    private int inuringObjectContractNodeInuringObjectContractNodeId;
//    private Integer outputInuringPltElementsInuringPltElementId;
//    private Integer inputInuringPltElementsInuringPltElementId;
//
//    @Id
//    @Column(name = "InuringObjectContractNodeInuringPltElementId", nullable = false, precision = 0)
//    public int getInuringObjectContractNodeInuringObjectContractNodeId() {
//        return inuringObjectContractNodeInuringObjectContractNodeId;
//    }
//
//    public void setInuringObjectContractNodeInuringObjectContractNodeId(int inuringObjectContractNodeInuringObjectContractNodeId) {
//        this.inuringObjectContractNodeInuringObjectContractNodeId = inuringObjectContractNodeInuringObjectContractNodeId;
//    }
//
//    @Basic
//    @Column(name = "FKoutputInuringPltElementsId", nullable = true, precision = 0)
//    public Integer getOutputInuringPltElementsInuringPltElementId() {
//        return outputInuringPltElementsInuringPltElementId;
//    }
//
//    public void setOutputInuringPltElementsInuringPltElementId(Integer outputInuringPltElementsInuringPltElementId) {
//        this.outputInuringPltElementsInuringPltElementId = outputInuringPltElementsInuringPltElementId;
//    }
//
//    @Basic
//    @Column(name = "FKinputInuringPltElementsId", nullable = true, precision = 0)
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
//        InuringObjectContractNodeInuringPltElementEntity that = (InuringObjectContractNodeInuringPltElementEntity) o;
//        return inuringObjectContractNodeInuringObjectContractNodeId == that.inuringObjectContractNodeInuringObjectContractNodeId &&
//                Objects.equals(outputInuringPltElementsInuringPltElementId, that.outputInuringPltElementsInuringPltElementId) &&
//                Objects.equals(inputInuringPltElementsInuringPltElementId, that.inputInuringPltElementsInuringPltElementId);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(inuringObjectContractNodeInuringObjectContractNodeId, outputInuringPltElementsInuringPltElementId, inputInuringPltElementsInuringPltElementId);
//    }
//}
