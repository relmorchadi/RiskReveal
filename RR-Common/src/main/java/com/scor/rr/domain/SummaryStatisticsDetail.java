package com.scor.rr.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "SummaryStatisticsDetail")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SummaryStatisticsDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SummaryStatisticsDetailId")
    private Long id;

    @Column(name = "Entity")
    private Long entity;

    @Column(name = "SummaryStatisticHeaderId")
    private Long summaryStatisticHeaderId;

    @Column(name = "PLTHeaderId")
    private Long pltHeaderId;

    @Column(name = "LossType")
    private String lossType;

    @Column(name = "CurveType")
    private String curveType;

//    @Column(name = "RP2")
//    private Double rp2;
//
//    @Column(name = "RP5")
//    private Double rp5;
//
//    @Column(name = "RP10")
//    private Double rp10;

}