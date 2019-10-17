package com.scor.rr.domain.entities.rms.exposuresummary;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * The persistent class for the RmsExposureSummaryItem database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "RmsExposureSummaryItem")
@Data
public class RmsExposureSummaryItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RmsExposureSummaryItemId")
    private Long rmsExposureSummaryItemId;
    @Column(name = "EDMId")
    private Long edmId;
    @Column(name = "PortfolioId")
    private Long portfolioId;
    @Column(name = "PortfolioType")
    private String portfolioType;
    @Column(name = "SummaryName")
    private String summaryName;
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
    @Column(name = "Peril")
    private String peril;
    @Column(name = "AnalysisRegionCode")
    private String analysisRegionCode;
    @Column(name = "CountryCode")
    private String countryCode;
    @Column(name = "Admin1Code")
    private String admin1Code;
    @Column(name = "LocationCount")
    private Long locationCount;
    @Column(name = "TotalTiv")
    private Double totalTiv;
    @Column(name = "ExposureCurrency")
    private String exposureCurrency;
    @Column(name = "ExposureCurrencyUSDRate")
    private Double exposureCurrencyUSDRate;
    @Column(name = "ConformedCurrency")
    private String conformedCurrency;
    @Column(name = "ConformedCurrencyUSDRate")
    private Double conformedCurrencyUSDRate;
    @Column(name = "RateDate")
    private Date rateDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RmsExposureSummaryId")
    private RmsExposureSummary rmsExposureSummary;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RmsExposureSummaryBisId")
    private RmsExposureSummaryBis rmsExposureSummaryBis;
}
