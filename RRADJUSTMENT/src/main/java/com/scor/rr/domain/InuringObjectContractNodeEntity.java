package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "InuringObjectContractNode", schema = "dbo", catalog = "RiskReveal")
public class InuringObjectContractNodeEntity {
    private int inuringObjectContractNodeId;
    private Integer inuringPackageId;
    private Integer claimsBasisId;
    private String contractId;
    private String subjectPremiumBasisId;

    @Id
    @Column(name = "InuringObjectContractNode_Id", nullable = false, precision = 0)
    public int getInuringObjectContractNodeId() {
        return inuringObjectContractNodeId;
    }

    public void setInuringObjectContractNodeId(int inuringObjectContractNodeId) {
        this.inuringObjectContractNodeId = inuringObjectContractNodeId;
    }

    @Basic
    @Column(name = "InuringPackage_Id", nullable = true, precision = 0)
    public Integer getInuringPackageId() {
        return inuringPackageId;
    }

    public void setInuringPackageId(Integer inuringPackageId) {
        this.inuringPackageId = inuringPackageId;
    }

    @Basic
    @Column(name = "ClaimsBasis_Id", nullable = true, precision = 0)
    public Integer getClaimsBasisId() {
        return claimsBasisId;
    }

    public void setClaimsBasisId(Integer claimsBasisId) {
        this.claimsBasisId = claimsBasisId;
    }

    @Basic
    @Column(name = "Contract_Id", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    @Basic
    @Column(name = "SubjectPremiumBasis_Id", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getSubjectPremiumBasisId() {
        return subjectPremiumBasisId;
    }

    public void setSubjectPremiumBasisId(String subjectPremiumBasisId) {
        this.subjectPremiumBasisId = subjectPremiumBasisId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InuringObjectContractNodeEntity that = (InuringObjectContractNodeEntity) o;
        return inuringObjectContractNodeId == that.inuringObjectContractNodeId &&
                Objects.equals(inuringPackageId, that.inuringPackageId) &&
                Objects.equals(claimsBasisId, that.claimsBasisId) &&
                Objects.equals(contractId, that.contractId) &&
                Objects.equals(subjectPremiumBasisId, that.subjectPremiumBasisId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inuringObjectContractNodeId, inuringPackageId, claimsBasisId, contractId, subjectPremiumBasisId);
    }
}
