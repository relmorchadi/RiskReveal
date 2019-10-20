package com.scor.rr.domain.entities.references;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

/**
 * The persistent class for the RegionPeril database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "RegionPeril")
@Data
public class RegionPeril {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "regionPerilId")
    private Integer regionPerilId;
    @Column(name = "regionPerilCode")
    private String regionPerilCode;
    @Column(name = "regionPerilDescription")
    private String regionPerilDesc;
    @Column(name = "perilCode")
    private String perilCode;
    @Column(name = "regionPerilGroupCode")
    private String regionPerilGroupCode;
    @Column(name = "regionPerilGroupDescription")
    private String regionPerilGroupDesc;
    @Column(name = "regionHierarchy")
    private Integer regionHierarchy;
    @Column(name = "regionDescription")
    private String regionDesc;
    @Column(name = "isModelled")
    private Boolean isModelled;
    @Column(name = "hierarchyParentCode")
    private String hierarchyParentCode;
    @Column(name = "hierarchyLevel")
    private String hierarchyLevel;
    @Column(name = "isMinimumGrainRegionPeril")
    private Boolean isMinimumGrainRegionPeril;
    @Column(name = "parentMinimumGrainRegionPeril")
    private String parentMinimumGrainRegionPeril;
    @Column(name = "LastUpdatedRiskReveal")
    private Date LastUpdatedRiskReveal;
    @Column(name = "LastUpdatedCatDomain")
    private Date LastUpdatedCatDomain;
    @Column(name = "LastSyncRunCatDomain")
    private Date LastSyncRunCatDomain;
    @Column(name = "isActive")
    private Boolean isActive;
    @Column(name = "comments")
    private String comments;
}
