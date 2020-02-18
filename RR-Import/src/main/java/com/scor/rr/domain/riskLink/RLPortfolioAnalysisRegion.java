package com.scor.rr.domain.riskLink;

import com.scor.rr.domain.EdmAllPortfolioAnalysisRegions;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "RLPortfolioAnalysisRegion")
@AllArgsConstructor
@NoArgsConstructor
public class RLPortfolioAnalysisRegion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rlPortfolioAnalysisRegionId;

    @Column(name = "Entity")
    private Integer entity;

    @Column(name = "AnalysisRegion")
    private String analysisRegion;

    @Column(name = "AnalysisRegionName")
    private String analysisRegionName;

    @Column(name = "Peril")
    private String peril;

    @Column(name = "TotalTIVinUSD")
    private Double totalTIVinUSD;

    @Column(name = "ExposureCurrency")
    private String exposureCurrency;

    @Column(name = "RateToCurrency")
    private Double rateToCurrency;

    @Column(name = "LocationCount")
    private Integer locationCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RlPortfolioId")
    private RLPortfolio rlPortfolio;

    public RLPortfolioAnalysisRegion(EdmAllPortfolioAnalysisRegions portfolioAnalysisRegions, RLPortfolio rlPortfolio) {
        this.analysisRegion = portfolioAnalysisRegions.getAnalysisRegion();
        this.entity = 1;
        this.analysisRegionName = portfolioAnalysisRegions.getAnalysisRegionDesc();
        this.peril = portfolioAnalysisRegions.getPeril();
        this.locationCount = portfolioAnalysisRegions.getLocCount();
        this.totalTIVinUSD = portfolioAnalysisRegions.getTotalTiv();
        this.exposureCurrency = portfolioAnalysisRegions.getExpoCurrency();
        this.rlPortfolio = rlPortfolio;
    }
}
