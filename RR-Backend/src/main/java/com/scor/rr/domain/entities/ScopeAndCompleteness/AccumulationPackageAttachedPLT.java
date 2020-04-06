package com.scor.rr.domain.entities.ScopeAndCompleteness;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "AccumulationPackageAttachedPLT")
public class AccumulationPackageAttachedPLT {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AccumulationPackageAttachedPLTid", nullable = false)
    private long accumulationPackageAttachedPLTid;

    @Column(name = "Entity")
    private int entity;

    @Column(name = "AccumulationPackageId")
    private long accumulationPackageId;

    @Column(name = "PLTHeaderId")
    private long pLTHeaderId;

    @Column(name = "ContractSectionId")//****??????
    private String contractSectionId;

    public AccumulationPackageAttachedPLT() {
    }
}
