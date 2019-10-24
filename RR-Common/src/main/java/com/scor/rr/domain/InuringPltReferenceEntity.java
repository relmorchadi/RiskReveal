//package com.scor.rr.domain;
//
//import javax.persistence.*;
//import java.util.Objects;
//
//@Entity
//@Table(name = "InuringPltReference", schema = "dbo", catalog = "RiskReveal")
//public class InuringPltReferenceEntity {
//    private int inuringPltReferenceId;
//    private Integer inuringPackageOperationId;
//
//    @Id
//    @Column(name = "InuringPltReferenceId", nullable = false, precision = 0)
//    public int getInuringPltReferenceId() {
//        return inuringPltReferenceId;
//    }
//
//    public void setInuringPltReferenceId(int inuringPltReferenceId) {
//        this.inuringPltReferenceId = inuringPltReferenceId;
//    }
//
//    @Basic
//    @Column(name = "InuringPackageOperationId", nullable = true, precision = 0)
//    public Integer getInuringPackageOperationId() {
//        return inuringPackageOperationId;
//    }
//
//    public void setInuringPackageOperationId(Integer inuringPackageOperationId) {
//        this.inuringPackageOperationId = inuringPackageOperationId;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        InuringPltReferenceEntity that = (InuringPltReferenceEntity) o;
//        return inuringPltReferenceId == that.inuringPltReferenceId &&
//                Objects.equals(inuringPackageOperationId, that.inuringPackageOperationId);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(inuringPltReferenceId, inuringPackageOperationId);
//    }
//}
