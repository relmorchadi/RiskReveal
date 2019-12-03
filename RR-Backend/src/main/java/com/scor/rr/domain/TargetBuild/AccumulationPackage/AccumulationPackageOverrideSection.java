package com.scor.rr.domain.TargetBuild.AccumulationPackage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "AccumulationPackageOverrideSection")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccumulationPackageOverrideSection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AccumulationPackageOverrideSectionId")
    private Long accumulationPackageOverrideSectionId;

    @Column(name = "Entity")
    private Integer entity;

    @Column(name = "AccumulationPackageId")
    private Long accumulationPackageId;

    @Column(name = "ContractSectionId")
    private String contractSectionId;

    @Column(name = "MinimumGrainRegionPerilCode", length = 25)
    private String minimumGrainRegionPerilCode;

    @Column(name = "AccumulationRAPCode", length = 50)
    private String accumulationRAPCode;

    @Column(name = "OverrideBasisCode")
    private String overrideBasisCode;

    @Column(name = "OverrideBasisNarrative")
    private String overrideBasisNarrative;

}
