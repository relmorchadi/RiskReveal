package com.scor.rr.domain.riskLink;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RlPortfolioAnalysisRegion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long rlPortfolioAnalysisRegionId;

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
}
