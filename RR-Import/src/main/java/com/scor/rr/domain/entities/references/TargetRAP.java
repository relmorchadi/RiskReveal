package com.scor.rr.domain.entities.references;

import lombok.Data;

import javax.persistence.*;
import java.io.File;
import java.sql.Date;

/**
 * The persistent class for the TargetRAP database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "TargetRAP")
@Data
public class TargetRAP {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TargetRAPId")
    private Integer targetRAPId;
    @Column(name = "ModellingVendor")
    private String modellingVendor;
    @Column(name = "ModellingSystem")
    private String modellingSystem;
    @Column(name = "ModellingSystemVersion")
    private String modellingSystemVersion;
    @Column(name = "TargetRapCode")
    private String targetRapCode;
    @Column(name = "TargetRapDesc")
    private String targetRapDesc;
    @Column(name = "PetID")
    private Integer petId;
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
    @Column(name = "RmsInstanceId")
    private String rmsInstanceId;
    @Column(name = "RmsName")
    private String rmsName;
    @Column(name = "RmsType")
    private String rmsType;
    @Column(name = "RmsVersionId")
    private Integer rmsVersionId;
    @Column(name = "RmscreateDate", columnDefinition = "date")
    private Date rmscreateDate;
    @Column(name = "RmsEnabled")
    private Boolean rmsEnabled;
    @Column(name = "RmsModelVersion")
    private String petRmsModelVersion;
    @Column(name = "PetModelVersionSuffixForxAct")
    private String petModelVersionSuffixForxAct;
    @Column(name = "PetRegion")
    private String petRegion;
    @Column(name = "PetPeril")
    private String petPeril;
    @Column(name = "PetDpmRegionPeril")
    private String petDpmRegionPeril;
    @Column(name = "PetPeqtFileName")
    private File petPeqtFileName;
    @Column(name = "PetSourceMaxPeriodPeqtFileName")
    private File petSourceMaxPeriodPeqtFileName;
    @Column(name = "isScorGenerated")
    private Boolean scorGenerated;
    @Column(name = "isScorCurrent")
    private Boolean scorCurrent;
    @Column(name = "isScorDefault")
    private Boolean scorDefault;
    @Column(name = "isActive")
    private Boolean active;
    @Column(name = "SourceRapCode")
    private String sourceRapCode;
}
