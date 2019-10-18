package com.scor.rr.domain.entities.plt;

import com.scor.rr.domain.entities.cat.File;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * The persistent class for the PET database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "PET")
@Data
public class PET {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "Id")
    private String id;
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
    @Column(name = "RmsSimulationSetId")
    private Integer rmsSimulationSetId;
    @Column(name = "RmsModelVersion")
    private String rmsModelVersion;
    @Column(name = "ModelVersionSuffixForxAct")
    private String modelVersionSuffixForxAct;
    @Column(name = "Region")
    private String region;
    @Column(name = "Peril")
    private String peril;
    @Column(name = "DpmRegionPeril")
    private String dpmRegionPeril;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PeqtFileId")
    private File peqtFile;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SourceMaxPeriodPeqtFileId")
    private File sourceMaxPeriodPeqtFile;

    public PET(String petTypeCode, String petTransformFunction) {
        this.petTypeCode = petTypeCode;
        this.petTransformFunction = petTransformFunction;
    }
}
