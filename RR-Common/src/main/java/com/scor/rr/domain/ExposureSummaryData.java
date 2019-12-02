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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "GlobalExposureViewId")
    private Long exposureSummaryDataId;


    // TODO: Review with viet
    private Long portfolioId;

    private String portfolioType;


    @Column(name = "CountryCode")
    private String countryCode;
    @Column(name = "PerilCode")
    private String perilCode;
    @Column(name = "Admin1Code")
    private String admin1Code;
    @Column(name = "RegionPerilCode")
    private String regionPerilCode;
    private String regionPerilGroupCode;
    private String analysisRegionCode;
    @Column(name = "Metric")
    private String metric;

    private String dimension1;

    private String dimension2;

    private String dimension3;

    private String dimension4;

    private Integer dimensionSort1;

    private Integer dimensionSort2;

    private Integer dimensionSort3;

    private Integer dimensionSort4;

    private String financialPerspective;

    private String exposureCurrency;

    private String conformedCurrency;

    @Column(name = "TIV")
    private Double tiv;
    @Column(name = "AvgTIV")
    private Double avgTiv;
    @Column(name = "LocationCount")
    private Long locationCount;

    private Double exposureCurrencyUSDRate;

    private Double conformedCurrencyUSDRate;

    private Date rateDate;


    @ManyToOne
    @JoinColumn(name = "GlobalViewSummaryId")
    private GlobalViewSummary globalViewSummary;
}
