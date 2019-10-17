package com.scor.rr.domain.entities.rms.exposuresummary;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

/**
 * The persistent class for the ExposureSummaryItem database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "ExposureSummaryItem")
@Data
public class ExposureSummaryItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ExposureSummaryItemId")
    private Long exposureSummaryItemId;
    @Column(name = "EDMId")
    private Long edmId;
    @Column(name = "PortfolioId")
    private Long portfolioId;
    @Column(name = "PortfolioType")
    private String portfolioType;
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
    @Column(name = "RegionPerilCode")
    private String regionPerilCode;
    @Column(name = "RegionPerilGroupCode")
    private String regionPerilGroupCode;
    @Column(name = "Peril")
    private String peril;
    @Column(name = "AnalysisRegionCode")
    private String analysisRegionCode;
    @Column(name = "CountryCode")
    private String countryCode;
    @Column(name = "Admin1Code")
    private String admin1Code;
    @Column(name = "ExposureCurrency")
    private String exposureCurrency;
    @Column(name = "ConformedCurrency")
    private String conformedCurrency;
    @Column(name = "LocationCount")
    private Long locationCount;
    @Column(name = "TotalTIV")
    private Double totalTIV;
    @Column(name = "ExposureCurrencyUSDRate")
    private Double exposureCurrencyUSDRate;
    @Column(name = "ConformedCurrencyUSDRate")
    private Double conformedCurrencyUSDRate;
    @Column(name = "RateDate")
    private Date rateDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ExposureSummaryId")
    private ExposureSummary exposureSummary;
}
