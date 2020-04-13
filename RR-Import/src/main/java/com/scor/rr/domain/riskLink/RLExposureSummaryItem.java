package com.scor.rr.domain.riskLink;

import com.scor.rr.domain.GlobalExposureViewSummary;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "SourceExposureSummaryItem")
@Data
public class RLExposureSummaryItem {

    @Id
    @Column(name = "SourceExposureSummaryItemId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rlExposureSummaryItemId;
    @Column(name = "Entity")
    private Long Entity;
    @Column(name = "ExposureSummaryName")
    private String ExposureSummaryName;
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
    @Column(name = "CountryCode")
    private String countryCode;
    @Column(name = "Admin1Code")
    private String admin1Code;
    @Column(name = "AnalysisRegionCode")
    private String analysisRegionCode;
    @Column(name = "ExposureCurrency")
    private String exposureCurrency;
    @Column(name = "ConformedCurrency")
    private String conformedCurrency;
    @Column(name = "LocationCount")
    private Long locationCount;
    @Column(name = "TotalTiv")
    private BigDecimal totalTiv;
    @Column(name = "ExposureCurrencyUSDRate")
    private Double exposureCurrencyUSDRate;
    @Column(name = "ConformedCurrencyUSDRate")
    private Double conformedCurrencyUSDRate;
    @Column(name = "FxRateVintageDate")
    private Date rateDate;

    @ManyToOne
    @JoinColumn(name = "GlobalExposureViewSummaryId")
    private GlobalExposureViewSummary globalViewSummary;

}
