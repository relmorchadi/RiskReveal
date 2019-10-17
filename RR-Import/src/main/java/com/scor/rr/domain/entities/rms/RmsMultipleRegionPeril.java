package com.scor.rr.domain.entities.rms;

import com.scor.rr.domain.entities.ihub.SourceResult;
import lombok.Data;

import javax.persistence.*;

/**
 * The persistent class for the RmsMultipleRegionPeril database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "RmsMultipleRegionPeril")
@Data
public class RmsMultipleRegionPeril {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RmsMultipleRegionPerilId")
    private Long rmsMultipleRegionPerilId;
    @Column(name = "RmsAnalysisId")
    private String rmsAnalysisId;
    @Column(name = "RDMId")
    private Long rdmId;
    @Column(name = "RDMName")
    private String rdmName;
    @Column(name = "AnlId")
    private Long anlId;
    @Column(name = "Region")
    private String region;
    @Column(name = "Peril")
    private String peril;
    @Column(name = "RegionPeril")
    private String regionPeril;
    @Column(name = "ProfileKey")
    private String profileKey;
    @Column(name = "EvtCount")
    private Long evtCount;
    @Column(name = "Percentage")
    private Double percentage;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SourceResultId")
    private SourceResult sourceResult;
}
