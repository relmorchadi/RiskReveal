package com.scor.rr.domain.riskLink;

import com.scor.rr.domain.dto.PortfolioSelectionDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "RLPortfolioSelection")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RLPortfolioSelection {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public RLPortfolioSelection(PortfolioSelectionDto portfolioImportSelection) {
        this.entity = 1L;
        this.targetCurrency = portfolioImportSelection.getTargetCurrency();
        this.unitMultiplier = portfolioImportSelection.getUnitMultiplier();
        this.proportion = portfolioImportSelection.getProportion();
        this.importLocationLevel = portfolioImportSelection.isImportLocationLevel();
        this.analysisRegions = portfolioImportSelection.getAnalysisRegions();
        this.projectId = portfolioImportSelection.getProjectId();
        RLPortfolio rlPortfolio = new RLPortfolio();
        rlPortfolio.setRlPortfolioId(portfolioImportSelection.getRlPortfolioId());
        this.rlPortfolio = rlPortfolio;
    }
}
