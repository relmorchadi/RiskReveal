package com.scor.rr.entity;


import lombok.Data;

import javax.persistence.*;

/**
 * Created by Soufiane Izend on 02/10/2019.
 */

@Entity
@Data
@Table(name = "RefFMFContractType", schema = "dbo", catalog = "RiskReveal")
public class RefFMFContractType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ContractTypeId", nullable = false)
    private long contractTypeId;

    @Column(name = "Entity")
    private int entity;

    @Column(name = "ContractTypeCode")
    private String contractTypeCode;

    @Column(name = "ContractTypeName")
    private String contractTypeName;

    @Column(name = "UsedInRR")
    private boolean usedInRR;

    @Column(name = "RRUISequence")
    private int rRUISequence;

    @Column(name = "MainDistribution")
    private String mainDistribution;

    @Column(name = "ContractClass")
    private String contractClass;

    @Column(name = "RRUILongName")
    private String rRUILongName;

    @Column(name = "IsActive")
    private boolean isActive;

    @Column(name = "MultipleLayer")
    private boolean multipleLayer;

    public RefFMFContractType() {
    }

    public boolean getMultipleLayer(){
        return this.multipleLayer;
    }
}
