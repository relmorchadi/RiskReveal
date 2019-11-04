//package com.scor.rr.domain;
//
//import javax.persistence.*;
//import java.util.Objects;
//
//@Entity
//@Table(name = "InuringOutboundPltConfig", schema = "dbo", catalog = "RiskReveal")
//public class InuringOutboundPltConfigEntity {
//    private int inuringOutboundPltConfigId;
//    private String contractId;
//
//    @Id
//    @Column(name = "InuringOutboundPltConfigId", nullable = false, precision = 0)
//    public int getInuringOutboundPltConfigId() {
//        return inuringOutboundPltConfigId;
//    }
//
//    public void setInuringOutboundPltConfigId(int inuringOutboundPltConfigId) {
//        this.inuringOutboundPltConfigId = inuringOutboundPltConfigId;
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
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        InuringOutboundPltConfigEntity that = (InuringOutboundPltConfigEntity) o;
//        return inuringOutboundPltConfigId == that.inuringOutboundPltConfigId &&
//                Objects.equals(contractId, that.contractId);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(inuringOutboundPltConfigId, contractId);
//    }
//}
