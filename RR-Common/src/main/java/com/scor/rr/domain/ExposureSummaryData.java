package com.scor.rr.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExposureSummaryData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ExposureSummaryDataId")
    private Long exposureSummaryDataId;
    @Column(name = "Entity")
    private Integer entity;
    // TODO: Review with viet
    @Column(name = "SourcePortfolioId")
    private Long sourcePortfolioId;
    @Column(name = "ModelPortfolioId")
    private Long modelPortfolioId;
    @Column(name = "PortfolioType")
    private String portfolioType;
    @Column(name = "CountryCode")
    private String countryCode;
    @Column(name = "PerilCode")
    private String perilCode;
    @Column(name = "Admin1Code")
    private String admin1Code;
    @Column(name = "RegionPerilCode")
    private String regionPerilCode;
    @Column(name = "RegionPerilGroupCode")
    private String regionPerilGroupCode;
    @Column(name = "AnalysisRegionCode")
    private String analysisRegionCode;
    @Column(name = "Dimension1")
    private String dimension1;
    @Column(name = "Dimension2")
    private String dimension2;
    @Column(name = "Dimension3")
    private String dimension3;
    @Column(name = "Dimension4")
    private String dimension4;
    @Column(name = "DimensionSort1")
    private Integer dimensionSort1;
    @Column(name = "DimensionSort2")
    private Integer dimensionSort2;
    @Column(name = "DimensionSort3")
    private Integer dimensionSort3;
    @Column(name = "DimensionSort4")
    private Integer dimensionSort4;
    @Column(name = "FinancialPerspective")
    private String financialPerspective;
    @Column(name = "ExposureCurrency")
    private String exposureCurrency;
    @Column(name = "Metric")
    private String metric;
    @Column(name = "ConformedCurrency")
    private String conformedCurrency;

    @Column(name = "TIV")
    private Double tiv;
    @Column(name = "AvgTIV")
    private Double avgTiv;
    @Column(name = "LocationCount")
    private Long locationCount;
    @Column(name = "ExposureCurrencyUSDRate")
    private Double exposureCurrencyUSDRate;
    @Column(name = "conformedCurrencyUSDRate")
    private Double conformedCurrencyUSDRate;
    @Column(name = "RateDate")
    private Date rateDate;


    @ManyToOne
    @JoinColumn(name = "GlobalViewSummaryId")
    private GlobalViewSummary globalViewSummary;
}
