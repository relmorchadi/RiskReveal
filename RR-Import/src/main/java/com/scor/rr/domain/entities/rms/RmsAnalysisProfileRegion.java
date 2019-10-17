package com.scor.rr.domain.entities.rms;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * The persistent class for the RmsAnalysisProfileRegion database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "RmsAnalysisProfileRegion")
@Data
public class RmsAnalysisProfileRegion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RmsAnalysisProfileRegionId")
    private Long rmsAnalysisProfileRegionId;
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
    @Column(name = "Aal")
    private BigDecimal aal;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RmsAnalysisId")
    private RmsAnalysis rmsAnalysis;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RmsAnalysisBasicId")
    private RmsAnalysisBasic rmsAnalysisBasic;

    public RmsAnalysisProfileRegion() {
    }

    public RmsAnalysisProfileRegion(String analysisRegion, String analysisRegionName, String profileRegion,
                                    String profileRegionName, String peril, BigDecimal aal) {
        super();
        this.analysisRegion = analysisRegion;
        this.analysisRegionName = analysisRegionName;
        this.profileRegion = profileRegion;
        this.profileRegionName = profileRegionName;
        this.peril = peril;
        this.aal = aal;
    }
}
