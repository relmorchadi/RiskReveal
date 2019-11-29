package com.scor.rr.domain.riskLink;

//import com.scor.rr.domain.GlobalViewSummary;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
@Data
public class RLExposureSummaryItem {

    @Id
    private Long rlExposureSummaryItemId;

    private Long portfolioId;

    private String portfolioType;

    private String summaryName;

    private String dimension1;

    private String dimension2;

    private String dimension3;

    private String dimension4;

    private Integer dimensionSort1;

    private Integer dimensionSort2;

    private Integer dimensionSort3;

    private Integer dimensionSort4;

    private String financialPerspective;

    private String peril;

    private String analysisRegionCode;

    private String countryCode;

    private String admin1Code;

    private Long locationCount;

    private Double totalTiv;

    private String exposureCurrency;

    private Double exposureCurrencyUSDRate;

    private String conformedCurrency;

    private Double conformedCurrencyUSDRate;

    private Date rateDate;

//    @ManyToOne
//    @JoinColumn(name = "GlobalViewSummaryId")
//    private GlobalViewSummary globalViewSummary;

}
