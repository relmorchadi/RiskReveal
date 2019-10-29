package com.scor.rr.domain.model;


import com.scor.rr.domain.enums.StatisticMetric;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class SummaryStatisticHeader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long summaryStatisticHeaderId;
    private Integer entity;
    private String lossDataType;
    private Long lossDataId;
    private String financialPerspective;
    private Double purePremium;
    private String currency;
    private Double standardDeviation;
    private StatisticMetric metric;
    private Double cov;
    private Double skewness;
    private Double kurtosis;
    private String ePSFilePath;
    private String ePSFileName;
    private Double oEP10;
    private Double oEP50;
    private Double oEP100;
    private Double oEP250;
    private Double oEP500;
    private Double oEP1000;
    private Double aEP10;
    private Double aEP50;
    private Double aEP100;
    private Double aEP250;
    private Double aEP500;
    private Double aEP1000;


    public SummaryStatisticHeader(Integer entity,String financialPerspective, Double cov, Double standardDeviation
            , Double purePremium, String lossDataType, Long lossDataId, String ePSFileName, String ePSFilePath) {
        this.entity = entity;
        this.lossDataType = lossDataType;
        this.lossDataId = lossDataId;
        this.financialPerspective = financialPerspective;
        this.purePremium = purePremium;
        this.standardDeviation = standardDeviation;
        this.cov = cov;
        this.ePSFilePath = ePSFilePath;
        this.ePSFileName = ePSFileName;
    }
}
