package com.scor.rr.domain.entities.omega;

import com.scor.rr.domain.entities.accumulation.AccumulationProfile;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the ContractSectionExpectedScope database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "ContractSectionExpectedScope")
@Data
public class ContractSectionExpectedScope {
    @Id
    @Column(name = "ContractSectionExpectedScopeId")
    private Long contractSectionExpectedScopeId;
    @Column(name = "Omega")
    private Boolean omega;
    @Column(name = "Imported")
    private Boolean imported;
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
    @Column(name = "BaseAdjustments")
    private String baseAdjustments;
    @Column(name = "DefaultAdjustments")
    private String defaultAdjustments;
    @Column(name = "AnalystAdjustments")
    private String analystAdjustments;
    @Column(name = "ClientAdjustments")
    private String clientAdjustments;
    @Column(name = "Narrative")
    private String narrative;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TreatySectionId")
    private Section section;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "AccumulationProfiles_ContractExpectedScopes", joinColumns = {
            @JoinColumn(name = "AccumulationProfileId")}, inverseJoinColumns = {@JoinColumn(name = "ContractId")})
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<AccumulationProfile> accumulationProfile;
}
