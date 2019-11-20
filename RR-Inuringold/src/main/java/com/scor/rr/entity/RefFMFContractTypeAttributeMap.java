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
    private int refFMFContractTypeAttributeMapId;

    @Column(name = "Entity")
    private int entity;

    @Column(name = "ContractTypeId")
    private int contractTypeId;

    @Column(name = "ContractAttributeId")
    private int contractAttributeId;

    @Column(name = "Flag")
    private int flag;

}
