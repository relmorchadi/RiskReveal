package com.scor.rr.domain.entities.plt;

import com.scor.rr.domain.entities.references.RegionPeril;
import lombok.Data;

import javax.persistence.*;

/**
 * The persistent class for the ModelRAPSource database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "ModelRAPSource")
@Data
public class ModelRAPSource {
    @Id
    @Column(name = "Id")
    private String id;
    @Column(name = "Name")
    private String name;
    @Column(name = "LossTableType")
    private String lossTableType;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RegionPerilId")
    private RegionPeril regionPeril;

}
