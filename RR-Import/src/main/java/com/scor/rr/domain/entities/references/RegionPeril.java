package com.scor.rr.domain.entities.references;

import lombok.Data;

import javax.persistence.*;

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
    @Column(name = "RegionPerilId")
    private Integer regionPerilId;
    @Column(name = "RegionPerilCode")
    private String regionPerilCode;
    @Column(name = "RegionPerilDescription")
    private String regionPerilDesc;
    @Column(name = "PerilCode")
    private String perilCode;
    @Column(name = "RegionPerilGroupCode")
    private String regionPerilGroupCode;
    @Column(name = "RegionPerilGroupDescription")
    private String regionPerilGroupDesc;
    @Column(name = "RegionHierarchy")
    private Integer regionHierarchy;
    @Column(name = "RegionDescription")
    private String regionDesc;
    @Column(name = "IsModelled")
    private Boolean isModelled;
    @Column(name = "HierarchyParentCode")
    private String hierarchyParentCode;
    @Column(name = "HierarchyLevel")
    private String hierarchyLevel;
    @Column(name = "IsMinimumGrainRegionPeril")
    private Boolean isMinimumGrainRegionPeril;
    @Column(name = "ParentMinimumGrainRegionPeril")
    private String parentMinimumGrainRegionPeril;
    @Column(name = "IsActive")
    private Boolean isActive;
}
