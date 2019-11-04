//package com.scor.rr.domain;
//
//import javax.persistence.*;
//import java.util.Objects;
//
//@Entity
//@Table(name = "InuringObjectEdge", schema = "dbo", catalog = "RiskReveal")
//public class InuringObjectEdgeEntity {
//    private int idUi;
//    private Integer targetInuringObjectNodeId;
//    private Integer sourceInuringObjectNodeId;
//    private Integer inuringPackageId;
//
//    @Id
//    @Column(name = "InuringObjectEdgeId", nullable = false, precision = 0)
//    public int getIdUi() {
//        return idUi;
//    }
//
//    public void setIdUi(int idUi) {
//        this.idUi = idUi;
//    }
//
//    @Basic
//    @Column(name = "FKTargetInuringObjectNodeId", nullable = true, precision = 0)
//    public Integer getTargetInuringObjectNodeId() {
//        return targetInuringObjectNodeId;
//    }
//
//    public void setTargetInuringObjectNodeId(Integer targetInuringObjectNodeId) {
//        this.targetInuringObjectNodeId = targetInuringObjectNodeId;
//    }
//
//    @Basic
//    @Column(name = "FKSourceInuringObjectNodeId", nullable = true, precision = 0)
//    public Integer getSourceInuringObjectNodeId() {
//        return sourceInuringObjectNodeId;
//    }
//
//    public void setSourceInuringObjectNodeId(Integer sourceInuringObjectNodeId) {
//        this.sourceInuringObjectNodeId = sourceInuringObjectNodeId;
//    }
//
//    @Basic
//    @Column(name = "FKInuringPackageId", nullable = true, precision = 0)
//    public Integer getInuringPackageId() {
//        return inuringPackageId;
//    }
//
//    public void setInuringPackageId(Integer inuringPackageId) {
//        this.inuringPackageId = inuringPackageId;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        InuringObjectEdgeEntity that = (InuringObjectEdgeEntity) o;
//        return idUi == that.idUi &&
//                Objects.equals(targetInuringObjectNodeId, that.targetInuringObjectNodeId) &&
//                Objects.equals(sourceInuringObjectNodeId, that.sourceInuringObjectNodeId) &&
//                Objects.equals(inuringPackageId, that.inuringPackageId);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(idUi, targetInuringObjectNodeId, sourceInuringObjectNodeId, inuringPackageId);
//    }
//}
