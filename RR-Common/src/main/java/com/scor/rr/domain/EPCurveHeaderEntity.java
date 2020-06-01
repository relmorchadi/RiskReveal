package com.scor.rr.domain;


import com.scor.rr.domain.enums.StatisticMetric;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "EPCurveHeader")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EPCurveHeaderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EPCurveHeaderId")
    private Long ePCurveHeaderId;
    @Column(name = "Entity")
    private Integer entity;
    @Column(name = "LossDataType")
    private String lossDataType;
    @Column(name = "LossDataId")
    private Long lossDataId;
    @Column(name = "FinancialPerspective")
    private String financialPerspective;
    @Column(name = "StatisticMetric")
    private StatisticMetric statisticMetric;
    @Column(name = "EPCurves")
    private String ePCurves;
    @Column(name = "EPCFilePath")
    private String ePCFilePath;
    @Column(name = "EPCFileName")
    private String ePCFileName;

    public EPCurveHeaderEntity(EPCurveHeaderEntity other) {
        this.entity = other.entity;
        this.lossDataType = other.lossDataType;
        this.lossDataId = other.lossDataId;
        this.financialPerspective = other.financialPerspective;
        this.statisticMetric = other.statisticMetric;
        this.ePCurves = other.ePCurves;
        this.ePCFilePath = other.ePCFilePath;
        this.ePCFileName = other.ePCFileName;

    }
}
