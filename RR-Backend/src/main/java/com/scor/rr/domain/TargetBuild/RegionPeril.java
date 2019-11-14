package com.scor.rr.domain.TargetBuild;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "RegionPeril", schema = "tb")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegionPeril {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RegionPerilId")
    private Integer regionPerilId;

    @Column(name = "RegionPerilCode")
    private String regionPerilCode;

    @Column(name = "RegionPerilDesc")
    private String regionPerilDesc;

    @Column(name = "PerilCode")
    private String perilCode;

    @Column(name = "RegionPerilGroupCode")
    private String regionPerilGroupCode;

    @Column(name = "RegionPerilGroupDesc")
    private String regionPerilGroupDesc;

    @Column(name = "RegionHierarchy")
    private Integer regionHierarchy;

    @Column(name = "RegionDesc")
    private String regionDesc;

    @Column(name = "IsModelled")
    private Boolean isModelled;

    @Column(name = "HierarchyParentCode")
    private String hierarchyParentCode;

    @Column(name = "HierarchyLevel")
    private Integer hierarchyLevel;

    @Column(name = "IsMinimumGrainRegionPeril")
    private Boolean isMinimumGrainRegionPeril;

    @Column(name = "ParentMinimumGrainRegionPeril")
    private String parentMinimumGrainRegionPeril;

    @Column(name = "RootRegionPeril")
    private String rootRegionPeril;

    @Column(name = "IsActive")
    private Boolean isActive;

}
