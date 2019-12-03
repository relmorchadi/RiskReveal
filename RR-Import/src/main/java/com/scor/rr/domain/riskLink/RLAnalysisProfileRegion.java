package com.scor.rr.domain.riskLink;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "RLAnalysisProfileRegion")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RLAnalysisProfileRegion {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "RlAnalysisProfileRegionId")
    private Long rlAnalysisProfileRegionId;

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
    @JoinColumn(name = "RlAnalysisId")
    private RLAnalysis rlAnalysis;
}
