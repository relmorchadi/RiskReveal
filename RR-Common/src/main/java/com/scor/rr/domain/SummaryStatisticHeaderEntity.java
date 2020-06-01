package com.scor.rr.domain;

import com.scor.rr.domain.enums.StatisticMetric;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "SummaryStatisticHeader")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SummaryStatisticHeaderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SummaryStatisticHeaderId")
    private Long summaryStatisticHeaderId;

    @Column(name = "LossDataType")
    private String lossDataType;

    @Column(name = "LossDataId")
    private Long lossDataId;

    @Column(name = "Entity")
    private Long entity;

    @Column(name = "FinancialPerspective")
    private String financialPerspective;

    @Column(name = "PurePremium")
    private Double purePremium;

    @Column(name = "Currency", length = 3)
    private String currency;

    @Column(name = "StandardDeviation")
    private Double standardDeviation;

//    @Column(name = "Metric")
//    private StatisticMetric metric;

    @Column(name = "Cov")
    private Double cov;

    @Column(name = "Skewness")
    private Integer skewness;

    @Column(name = "Kurtosis")
    private Integer kurtosis;

    @Column(name = "EPSFilePath")
    private String ePSFilePath;

    @Column(name = "EPSFileName")
    private String ePSFileName;

    @Column(name = "OEP2")
    private Double oep2;

    @Column(name = "OEP5")
    private Double oep5;

    @Column(name = "OEP10")
    private Double oep10;

    @Column(name = "OEP50")
    private Double oep50;

    @Column(name = "OEP100")
    private Double oep100;

    @Column(name = "OEP250")
    private Double oep250;

    @Column(name = "OEP500")
    private Double oep500;

    @Column(name = "OEP1000")
    private Double oep1000;

    @Column(name = "AEP2")
    private Double aep2;

    @Column(name = "AEP5")
    private Double aep5;

    @Column(name = "AEP10")
    private Double aep10;

    @Column(name = "AEP50")
    private Double aep50;

    @Column(name = "AEP100")
    private Double aep100;

    @Column(name = "AEP250")
    private Double aep250;

    @Column(name = "AEP500")
    private Double aep500;

    @Column(name = "AEP1000")
    private Double aep1000;

    public SummaryStatisticHeaderEntity(Long entity, String financialPerspective, Double cov, Double standardDeviation
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

    public SummaryStatisticHeaderEntity(SummaryStatisticHeaderEntity other, boolean clone) {

        this.lossDataType = other.lossDataType;
        this.lossDataId = other.lossDataId;
        this.entity = other.entity;
        this.financialPerspective = other.financialPerspective;
        this.purePremium = other.purePremium;
        this.currency = other.currency;
        this.standardDeviation = other.standardDeviation;
        this.cov = other.cov;
        this.skewness = other.skewness;
        this.kurtosis = other.kurtosis;
        this.ePSFilePath = other.ePSFilePath;
        this.ePSFileName = other.ePSFileName;
        this.oep2 = other.oep2;
        this.oep5 = other.oep5;
        this.oep10 = other.oep10;
        this.oep50 = other.oep50;
        this.oep100 = other.oep100;
        this.oep250 = other.oep250;
        this.oep500 = other.oep500;
        this.oep1000 = other.oep1000;
        this.aep2 = other.aep2;
        this.aep5 = other.aep5;
        this.aep10 = other.aep10;
        this.aep50 = other.aep50;
        this.aep100 = other.aep100;
        this.aep250 = other.aep250;
        this.aep500 = other.aep500;
        this.aep1000 = other.aep1000;

    }
    public SummaryStatisticHeaderEntity(SummaryStatisticHeaderEntity summaryStatisticHeaderEntity) {
        this.entity = summaryStatisticHeaderEntity.getEntity();
        this.financialPerspective = summaryStatisticHeaderEntity.getFinancialPerspective();
        this.lossDataType = summaryStatisticHeaderEntity.getLossDataType();
    }
}
