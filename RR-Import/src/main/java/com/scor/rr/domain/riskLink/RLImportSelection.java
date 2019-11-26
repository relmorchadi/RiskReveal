package com.scor.rr.domain.riskLink;

import com.scor.rr.domain.RdmAnalysis;
import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "RLImportSelection")
@AllArgsConstructor
@NoArgsConstructor
public class RLImportSelection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RlSourceResultId")
    private Long rlSourceResultId;
    @Column(name = "RREntity")
    private Integer entity;
    @Column(name = "projectId")
    private Long projectId;
    @Column(name = "targetCurrency")
    private String targetCurrency;
    @Column(name = "targetRegionPeril")
    private String targetRegionPeril;
    @Column(name="UserSelectedGrain")
    private String userSelectedGrain;
    @Column(name = "overrideRegionPerilBasis")
    private String overrideRegionPerilBasis;
    @Column(name = "occurrenceBasis")
    private String occurrenceBasis;
    @Column(name = "financialPerspective")
    private String financialPerspective;
    @Column(name = "unitMultiplier")
    private Float unitMultiplier;
    @Column(name = "proportion")
    private Float proportion;
    @Column(name = "targetRAPCode")
    private String targetRAPCode;
    // TODO : Review with shaun
    @Column(name = "ProxyScalingBasis")
    private String proxyScalingBasis;
    @Column(name = "ProxyScalingNarrative")
    private String proxyScalingNarrative;
    @Column(name = "MultiplierBasis")
    private String multiplierBasis;
    @Column(name = "MultiplierNarrative")
    private String multiplierNarrative;
    //

    @ManyToOne
    @JoinColumn(name = "rlAnalysisId")
    private RLAnalysis rlAnalysis;

    public RLImportSelection(RdmAnalysis analysis, Long projectId){
        this.entity=1;
        this.projectId = projectId;
        this.targetCurrency = analysis.getAnalysisCurrency();
        //this.targetRegionPeril; // TODO: Calculate
        this.overrideRegionPerilBasis = null;
    }


}
