package com.scor.rr.domain.entities.plt;

import com.scor.rr.domain.entities.references.RegionPeril;
import lombok.Data;

import javax.persistence.*;

/**
 * The persistent class for the ModelRAP database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "ModelRAP")
@Data
public class ModelRAP {
    @Id
    @Column(name = "Id")
    private String id;
    @Column(name = "Name")
    private String name;
    @Column(name = "Description")
    private String description;
    @Column(name = "DefaultRAP")
    private Boolean defaultRAP;
    @Column(name = "LossTableType")
    private String lossTableType;
    @Column(name = "RegionPerilDefault")
    private Boolean regionPerilDefault;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PETId")
    private PET pet;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RegionPerilId")
    private RegionPeril regionPeril;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ModelRAPSourceId")
    private ModelRAPSource modelRAPSource;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ModellingSystemVersionId")
    private ModellingSystemVersion modellingSystemVersion;
}
