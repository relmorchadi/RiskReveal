package com.scor.rr.domain.TargetBuild;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "RRSummaryStatisticHeader", schema = "tb")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RRSummaryStatisticHeader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RRSummaryStatisticHeaderId")
    private Integer rRSummaryStatisticHeaderId;

    @Column(name = "LossDataType")
    private Integer lossDataType;

    @Column(name = "LossDataId")
    private Integer lossDataId;

    @Column(name = "Entity")
    private Integer entity;

    @Column(name = "FinancialPerspective")
    private Integer financialPerspective;

    @Column(name = "PurePremium")
    private Integer purePremium;

    @Column(name = "Currency")
    private Integer currency;

    @Column(name = "StandardDeviation")
    private Integer standardDeviation;

    @Column(name = "Cov")
    private Integer cov;

    @Column(name = "Skewness")
    private Integer skewness;

    @Column(name = "Kurtosis")
    private Integer kurtosis;

    @Column(name = "EPSFilePath")
    private Integer ePSFilePath;

    @Column(name = "EPSFileName")
    private Integer ePSFileName;

    @Column(name = "OEP10")
    private Integer oep10;

    @Column(name = "OEP50")
    private Integer oep50;

    @Column(name = "OEP100")
    private Integer oep100;

    @Column(name = "OEP250")
    private Integer oep250;

    @Column(name = "OEP500")
    private Integer oep500;

    @Column(name = "OEP1000")
    private Integer oep1000;

    @Column(name = "AEP10")
    private Integer aep10;

    @Column(name = "AEP50")
    private Integer aep50;

    @Column(name = "AEP100")
    private Integer aep100;

    @Column(name = "AEP250")
    private Integer aep250;

    @Column(name = "AEP500")
    private Integer aep500;

    @Column(name = "AEP1000")
    private Integer aep1000;

}
