package com.scor.rr.domain.entities.ScopeAndCompleteness;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "AccumulationPackageOverrideSection")
public class AccumulationPackageOverrideSection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AccumulationPackageOverrideSectionId", nullable = false)
    private long accumulationPackageOverrideSectionId;

    @Column(name = "Entity")
    private int entity;

    @Column(name = "AccumulationPackageId")
    private long accumulationPackageId;

    @Column(name = "ContractSectionId")
    private String contractSectionId;

    @Column(name = "MinimumGrainRegionPerilCode")
    private String minimumGrainRegionPerilCode;

    @Column(name = "AccumulationRAPCode")
    private String accumulationRAPCode;

    @Column(name = "OverrideBasisCode")
    private String overrideBasisCode;

    @Column(name = "OverrideBasisNarrative")
    private String overrideBasisNarrative;
}
