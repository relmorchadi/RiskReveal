package com.scor.rr.entity;

import com.scor.rr.enums.InuringNodeStatus;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by Soufiane Izend on 01/10/2019.
 */

@Entity
@Table(name = "InuringContractNode", schema = "dbo", catalog = "RiskReveal")
@Data
@Getter
public class InuringContractNode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "InuringContractNodeId", nullable = false)
    private long inuringContractNodeId;

    @Column(name = "Entity")
    private int entity;

    @Column(name = "InuringPackageId", nullable = false)
    private long inuringPackageId;

    @Column(name = "ContractNodeStatus", nullable = false)
    private InuringNodeStatus contractNodeStatus;

    @Column(name = "ContractTypeCode", nullable = false)
    private String contractTypeCode;

    @Column(name = "ContractName")
    private String contractName;

    @Column(name = "OccurenceBasis")
    private String occurenceBasis;

    @Column(name = "ClaimsBasis")
    private String claimsBasis;

    @Column(name = "ContractCurrency")
    private String contractCurrency;

    @Column(name = "SubjectPremium")
    private BigDecimal subjectPremium;

    public InuringContractNode() {
    }

    public InuringContractNode(long inuringPackageId,String contractTypeCode) {
        this.inuringPackageId = inuringPackageId;
        this.entity = 1;
        this.contractNodeStatus = InuringNodeStatus.Invalid;
        this.contractTypeCode = contractTypeCode;
    }
}
