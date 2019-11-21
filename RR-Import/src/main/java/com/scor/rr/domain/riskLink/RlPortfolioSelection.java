package com.scor.rr.domain.riskLink;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RlPortfolioSelection {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "RLPortfolioSelectionId")
    private Long rlPortfolioSelectionId;
    @Column(name = "Entity")
    private Long entity;
    @Column(name = "ProjectId")
    private Long projectId;
    @Column(name = "TargetCurrency")
    private String targetCurrency;
    @Column(name = "UnitMultiplier")
    private Double unitMultiplier;
    @Column(name = "Proportion")
    private Double proportion;

    // TODO : Review with Viet later
    @Column(name = "ImportLocationLevel")
    private boolean importLocationLevel;

    // TODO : Review with Viet later
    @Column(name = "AnalysisRegions")
    private String analysisRegions;

    @ManyToOne
    @JoinColumn(name = "RLPortfolioId")
    private RLPortfolio rlPortfolio;
}
