package com.scor.rr.domain.views;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "vw_RLAnalysisToRegionPerils")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RLAnalysisToRegionPeril {

    @Id
    @Column(name = "Id")
    private Long id;
    @Column(name = "RlModelAnalysisId")
    private Long rlModelAnalysisId;
    @Column(name = "AnalysisId")
    private Long analysisId;
    @Column(name = "AnalysisRegionPerilCode")
    private String analysisRPCode;
    @Column(name = "HierarchyParentCode")
    private String hierarchyParentCode;
    @Column(name = "ParentMinimumGrainRegionPeril")
    private String parentMinimumGrainRegionPeril;
    @Column(name = "RegionPerilCode")
    private String regionPerilCode;
    @Column(name = "RegionPerilDesc")
    private String regionPerilDesc;
}
