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
    private int contractTypeId;

    @Column(name = "Entity")
    private int entity;

    @Column(name = "ContractTypeCode")
    private String contractTypeCode;

    @Column(name = "ContractTypeName")
    private String contractTypeName;

    @Column(name = "RiskReveal")
    private boolean usedInRR;

    @Column(name = "UISequence")
    private int rRUISequence;

    @Column(name = "MainDistributionChannel")
    private String mainDistribution;

    @Column(name = "ContractClass")
    private String contractClass;

    @Column(name = "UIHoverName")
    private String rRUILongName;

    @Column(name = "IsActive")
    private boolean isActive;

    @Column(name = "MultipleLayer")
    private boolean multipleLayer;

    public boolean getMultipleLayer(){
        return this.multipleLayer;
    }
}
