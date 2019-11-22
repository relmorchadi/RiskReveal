package com.scor.rr.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class PET {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String petTypeCode;
    private String petTransformFunction;
    private Integer numberSimulationPeriods;
    private Integer sourceMaxSimulationPeriods;
    private String simulationPeriodBasisCode;
    private String petDescription;
    private Integer rmsSimulationSetId;
    private String rmsModelVersion;
    private String modelVersionSuffixForxAct;
    private String region;
    private String peril;
    private String dpmRegionPeril;
    private String peqtFilePath;
    private String peqtFileName;
    private String peqtFileFQN;
    private String sourceMaxPeriodPeqtFilePath;

}
