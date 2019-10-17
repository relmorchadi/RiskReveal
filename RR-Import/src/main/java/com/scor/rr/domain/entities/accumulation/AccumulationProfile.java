package com.scor.rr.domain.entities.accumulation;

import com.scor.rr.domain.entities.omega.UpdatedContractSectionExpectedScope;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

/**
 * The persistent class for the AccumulationProfile database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "AccumulationProfile")
@Data
public class AccumulationProfile {
    @Id
    @Column(name = "AccumulationProfileId")
    private Long accumulationProfileId;
    @Column(name = "AccumulationName")
    private String accumulationName;
    @Column(name = "Active")
    private Boolean active;
    @Column(name = "AnalystAdjustments")
    private String analystAdjustments;
    @Column(name = "BaseAdjustments")
    private String baseAdjustments;
    @Column(name = "ClientAdjustments")
    private String clientAdjustments;
    @Column(name = "DefaultAdjustments")
    private String defaultAdjustments;
    @Column(name = "EffectiveFrom")
    private Date effectiveFrom;
    @Column(name = "EffectiveTo")
    private Date effectiveTo;
    @Column(name = "LastSyncRunCatDomain")
    private Date lastSyncRunCatDomain;
    @Column(name = "LastUpdatedARC")
    private Date lastUpdatedARC;
    @Column(name = "LastUpdatedCatDomain")
    private Date lastUpdatedCatDomain;
    @Column(name = "MinimumGrainCodeIso2")
    private String minimumGrainCodeIso2;
    @Column(name = "MinimumGrainRegionPerilCode")
    private String minimumGrainRegionPerilCode;
    @Column(name = "Mandatory")
    private Boolean mandatory;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AccumulationRAPId")
    private AccumulationRAP accumulationRAP;
    @OneToMany(mappedBy = "accumulationProfile")
    private List<UpdatedContractSectionExpectedScope> updatedContractSectionExpectedScopes;

}
