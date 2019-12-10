package com.scor.rr.domain.riskLink;

import com.scor.rr.domain.RdmAnalysis;
import com.scor.rr.domain.dto.ImportSelectionDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "RLImportSelection")
@AllArgsConstructor
@NoArgsConstructor
public class RLImportSelection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RLImportSelectionId")
    private Long rlImportSelectionId;
    @Column(name = "Entity")
    private Integer entity;
    @Column(name = "ProjectId")
    private Long projectId;
    @Column(name = "TargetCurrency")
    private String targetCurrency;
    @Column(name = "TargetRegionPeril")
    private String targetRegionPeril;
    @Column(name = "UserSelectedGrain")
    private String userSelectedGrain;
    @Column(name = "OverrideRegionPerilBasis")
    private String overrideRegionPerilBasis;
    @Column(name = "OccurrenceBasis")
    private String occurrenceBasis;
    @Column(name = "FinancialPerspective")
    private String financialPerspective;
    @Column(name = "UnitMultiplier")
    private Float unitMultiplier;
    @Column(name = "Proportion")
    private Float proportion;
    @Column(name = "TargetRAPCode")
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
    @JoinColumn(name = "RLAnalysisId")
    private RLAnalysis rlAnalysis;

    public RLImportSelection(RdmAnalysis analysis, Long projectId) {
        this.entity = 1;
        this.projectId = projectId;
        this.targetCurrency = analysis.getAnalysisCurrency();
        //this.targetRegionPeril; // TODO: Calculate
        this.overrideRegionPerilBasis = null;
    }


    public RLImportSelection(ImportSelectionDto importSelectionDto) {
        this.projectId = importSelectionDto.getProjectId();
        this.entity = 1;
        this.targetCurrency = importSelectionDto.getTargetCurrency();
        this.targetRegionPeril = importSelectionDto.getTargetRegionPeril();
        this.unitMultiplier = importSelectionDto.getUnitMultiplier();
        this.proportion = importSelectionDto.getProportion();
        this.targetRAPCode = importSelectionDto.getTargetRAPCode();
        this.financialPerspective = importSelectionDto.getFinancialPerspective();
        RLAnalysis rlAnalysis = new RLAnalysis();
        rlAnalysis.setRlAnalysisId(importSelectionDto.getRlAnalysisId());
        this.rlAnalysis = rlAnalysis;
    }
}
