package com.scor.rr.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "PET")
@Data
@NoArgsConstructor
public class PetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PetId")
    private Long id;
    @Column(name = "PetTypeCode")
    private String petTypeCode;
    @Column(name = "PetTransformFunction")
    private String petTransformFunction;
    @Column(name = "NumberSimulationPeriods")
    private Integer numberSimulationPeriods;
    @Column(name = "SourceMaxSimulationPeriods")
    private Integer sourceMaxSimulationPeriods;
    @Column(name = "SimulationPeriodBasisCode")
    private String simulationPeriodBasisCode;
    @Column(name = "PetDescription")
    private String petDescription;
    @Column(name = "RLSimulationSetId")
    private Integer rlSimulationSetId;
    @Column(name = "RLModelVersion")
    private String rlModelVersion;
    @Column(name = "ModelVersionSuffixForxAct")
    private String modelVersionSuffixForxAct;
    @Column(name = "Region")
    private String region;
    @Column(name = "Peril")
    private String peril;
    @Column(name = "DpmRegionPeril")
    private String dpmRegionPeril;
    @Column(name = "PeqtFilePath")
    private String peqtFilePath;
    @Column(name = "PeqtFileName")
    private String peqtFileName;
    @Column(name = "PeqtFileFQN")
    private String peqtFileFQN;
    @Column(name = "SourceMaxPeriodPeqtFilePath")
    private String sourceMaxPeriodPeqtFilePath;

}
