package com.scor.rr.domain.riskLink;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.scor.rr.domain.RdmAnalysis;
import com.scor.rr.domain.dto.ImportSelectionDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    @Column(name = "OccurrenceBasisOverrideReason")
    private String occurrenceBasisOverrideReason;
    @Column(name = "FinancialPerspective")
    private String financialPerspective;
    @Column(name = "UnitMultiplier")
    private Float unitMultiplier;
    @Column(name = "Proportion")
    private Float proportion;
    @Column(name = "Division")
    private Integer division;

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

    @Column(name = "SystemRegionPeril")
    private String systemRegionPeril;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RLAnalysisId")
    @JsonBackReference
    private RLAnalysis rlAnalysis;

    @OneToMany(mappedBy = "rlImportSelection", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RLImportTargetRAPSelection> targetRaps;

    public RLImportSelection(RdmAnalysis analysis, Long projectId) {
        this.entity = 1;
        this.projectId = projectId;
        this.targetCurrency = analysis.getAnalysisCurrency();
        //this.targetRegionPeril; // TODO: Calculate
        this.overrideRegionPerilBasis = null;
        this.targetRaps = new ArrayList<>();
    }


    public RLImportSelection(ImportSelectionDto importSelectionDto, String fp, RLAnalysis rlAnalysis) {
        this.projectId = importSelectionDto.getProjectId();
        this.entity = 1;
        this.targetCurrency = importSelectionDto.getTargetCurrency();
        this.targetRegionPeril = importSelectionDto.getTargetRegionPeril();
        this.unitMultiplier = importSelectionDto.getUnitMultiplier();
        this.proportion = importSelectionDto.getProportion();
        this.financialPerspective = fp;
        this.rlAnalysis = rlAnalysis;
        this.targetRaps = new ArrayList<>();
        this.occurrenceBasis = importSelectionDto.getOccurrenceBasis();
        this.occurrenceBasisOverrideReason = importSelectionDto.getOccurrenceBasisOverrideReason();
    }

    public RLImportSelection(ImportSelectionDto importSelectionDto, String fp, RLAnalysis rlAnalysis, Integer division) {
        this(importSelectionDto, fp, rlAnalysis);
        this.division = division;
    }

    public void addTargetRap(RLImportTargetRAPSelection rlImportTargetRAPSelection) {
        this.targetRaps.add(rlImportTargetRAPSelection);
    }
}
