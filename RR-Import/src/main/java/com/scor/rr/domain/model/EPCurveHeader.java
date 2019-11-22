package com.scor.rr.domain.model;


import com.scor.rr.domain.enums.StatisticMetric;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@Builder
public class EPCurveHeader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ePCurveHeaderId;
    private Integer entity;
    private String lossDataType;
    private Long lossDataId;
    private String financialPerspective;
    private StatisticMetric statisticMetric;
    private String ePCurves;
    private String ePCFilePath;
    private String ePCFileName;

//    public EPCurveHeader(EPCurveHeader epCurveHeader){
//        this.entity = epCurveHeader.getEntity();
//        this.lossDataType = epCurveHeader.getLossDataType();
//        this.financialPerspective = epCurveHeader.getFinancialPerspective();
//        this.statisticMetric = epCurveHeader.getStatisticMetric();
//    }

}
