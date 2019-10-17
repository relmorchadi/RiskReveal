package com.scor.rr.domain.entities.omega;

import com.scor.rr.domain.entities.accumulation.AccumulationProfile;
import lombok.Data;

import javax.persistence.*;

/**
 * The persistent class for the UpdatedContractSectionExpectedScope database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "UpdatedContractSectionExpectedScope")
@Data
public class UpdatedContractSectionExpectedScope {
    @Id
    @Column(name = "UpdatedContractSectionExpectedScopeId")
    private Long updatedContractSectionExpectedScopeId;
    @Column(name = "WorkspaceId")
    private String workspaceId;
    @Column(name = "ModellingVendor")
    private String modellingVendor;
    @Column(name = "ModellingSystem")
    private String modellingSystem;
    @Column(name = "PerilCode")
    private String perilCode;
    @Column(name = "RootRegionPerilCode")
    private String rootRegionPerilCode;
    @Column(name = "RegionPerilId")
    private Integer regionPerilId;
    @Column(name = "RegionPerilCode")
    private String regionPerilCode;
    @Column(name = "RegionPerilDesc")
    private String regionPerilDesc;
    @Column(name = "MinimumGrainRegionPerilCode")
    private String minimumGrainRegionPerilCode;
    @Column(name = "Omega")
    private Boolean omega;
    @Column(name = "Imported")
    private Boolean imported;
    @Column(name = "Narrative")
    private String narrative;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TreatySectionId")
    private Section section;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AccumulationProfileId")
    private AccumulationProfile accumulationProfile;
}
