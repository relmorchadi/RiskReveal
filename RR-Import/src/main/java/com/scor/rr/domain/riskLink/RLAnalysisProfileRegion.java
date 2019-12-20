package com.scor.rr.domain.riskLink;

import com.scor.rr.domain.RdmAllAnalysisProfileRegions;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "ZZ_RLAnalysisProfileRegion")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RLAnalysisProfileRegion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RlAnalysisProfileRegionId")
    private Long rlAnalysisProfileRegionId;

    @Column(name = "Entity")
    private Integer entity;

    @Column(name = "AnalysisRegion")
    private String analysisRegion;

    @Column(name = "AnalysisRegionName")
    private String analysisRegionName;

    @Column(name = "ProfileRegion")
    private String profileRegion;

    @Column(name = "ProfileRegionName")
    private String profileRegionName;

    @Column(name = "Peril")
    private String peril;

    @Column(name = "AAL")
    private BigDecimal aal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RLModelAnalysisId")
    private RLAnalysis rlAnalysis;


    public RLAnalysisProfileRegion(RdmAllAnalysisProfileRegions rdmAllAnalysisProfileRegions, RLAnalysis rlAnalysis) {

        this.analysisRegion = rdmAllAnalysisProfileRegions.getAnalysisRegion();
        this.analysisRegionName = rdmAllAnalysisProfileRegions.getAnalysisRegionName();
        this.profileRegion = rdmAllAnalysisProfileRegions.getProfileRegion();
        this.profileRegionName = rdmAllAnalysisProfileRegions.getProfileRegionName();
        this.peril = rdmAllAnalysisProfileRegions.getPeril();
        this.aal = rdmAllAnalysisProfileRegions.getAal();
        this.rlAnalysis = rlAnalysis;
    }
}
