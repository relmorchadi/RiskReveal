//package com.scor.rr.domain;
//
//import javax.persistence.*;
//import java.util.Objects;
//
//@Entity
//@Table(name = "InuringObjectContractNode", schema = "dbo", catalog = "RiskReveal")
//public class InuringObjectContractNodeEntity {
//    private int inuringObjectContractNodeId;
//    private Integer inuringPackageId;
//    private Integer claimsBasisId;
//    private String contractId;
//    private String subjectPremiumBasisId;
//
//    @Id
//    @Column(name = "InuringObjectContractNodeId", nullable = false, precision = 0)
//    public int getInuringObjectContractNodeId() {
//        return inuringObjectContractNodeId;
//    }
//
//    public void setInuringObjectContractNodeId(int inuringObjectContractNodeId) {
//        this.inuringObjectContractNodeId = inuringObjectContractNodeId;
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
//    @Basic
//    @Column(name = "FKClaimsBasisId", nullable = true, precision = 0)
//    public Integer getClaimsBasisId() {
//        return claimsBasisId;
//    }
//
//    public void setClaimsBasisId(Integer claimsBasisId) {
//        this.claimsBasisId = claimsBasisId;
//    }
//
//    @Basic
//    @Column(name = "FKContractId", nullable = true, length = 255)
//    public String getContractId() {
//        return contractId;
//    }
//
//    public void setContractId(String contractId) {
//        this.contractId = contractId;
//    }
//
//    @Basic
//    @Column(name = "FKSubjectPremiumBasisId", nullable = true, length = 255)
//    public String getSubjectPremiumBasisId() {
//        return subjectPremiumBasisId;
//    }
//
//    public void setSubjectPremiumBasisId(String subjectPremiumBasisId) {
//        this.subjectPremiumBasisId = subjectPremiumBasisId;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        InuringObjectContractNodeEntity that = (InuringObjectContractNodeEntity) o;
//        return inuringObjectContractNodeId == that.inuringObjectContractNodeId &&
//                Objects.equals(inuringPackageId, that.inuringPackageId) &&
//                Objects.equals(claimsBasisId, that.claimsBasisId) &&
//                Objects.equals(contractId, that.contractId) &&
//                Objects.equals(subjectPremiumBasisId, that.subjectPremiumBasisId);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(inuringObjectContractNodeId, inuringPackageId, claimsBasisId, contractId, subjectPremiumBasisId);
//    }
//}
