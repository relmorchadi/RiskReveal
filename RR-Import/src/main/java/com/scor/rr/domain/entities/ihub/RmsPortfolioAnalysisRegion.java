package com.scor.rr.domain.entities.ihub;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * The persistent class for the RmsPortfolioAnalysisRegion database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "RmsPortfolioAnalysisRegion")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RmsPortfolioAnalysisRegion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;
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
    @Column(name = "RmsRateToCurrency")
    private Double rmsRateToCurrency;
    @Column(name = "LocationCount")
    private Integer locationCount;

    public RmsPortfolioAnalysisRegion(String analysisRegion, String analysisRegionName, String peril, Double totalTIVinUSD, String exposureCurrency, Double rmsRateToCurrency, Integer locationCount) {
        this.analysisRegion = analysisRegion;
        this.analysisRegionName = analysisRegionName;
        this.peril = peril;
        this.totalTIVinUSD = totalTIVinUSD;
        this.exposureCurrency = exposureCurrency;
        this.rmsRateToCurrency = rmsRateToCurrency;
        this.locationCount = locationCount;
    }
}
