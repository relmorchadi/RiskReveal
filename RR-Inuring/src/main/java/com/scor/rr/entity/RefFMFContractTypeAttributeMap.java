package com.scor.rr.entity;


import lombok.Data;

import javax.persistence.*;

/**
 * Created by Soufiane Izend on 02/10/2019.
 */

@Entity
@Data
@Table(name = "RefFMFContractTypeAttributeMap", schema = "dbo", catalog = "RiskReveal")
public class RefFMFContractTypeAttributeMap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RefFMFContractTypeAttributeMapId", nullable = false)
    private long refFMFContractTypeAttributeMapId;

    @Column(name = "RREntity")
    private int entity;

    @Column(name = "ContractTypeId")
    private long contractTypeId;

    @Column(name = "ContractAttributeId")
    private long contractAttributeId;

    @Column(name = "Flag")
    private int flag;

    public RefFMFContractTypeAttributeMap() {
    }
}
