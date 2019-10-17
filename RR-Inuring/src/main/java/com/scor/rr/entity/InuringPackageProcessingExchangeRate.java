package com.scor.rr.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Soufiane Izend on 01/10/2019.
 */

@Entity
@Data
@Table(name = "InuringPackageProcessingExchangeRate", schema = "dbo", catalog = "RiskReveal")
public class InuringPackageProcessingExchangeRate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "InuringPackageProcessingExchangeRateId", nullable = false)
    private int inuringPackageProcessingExchangeRateId;

    @Column(name = "Entity")
    private int entity;

    @Column(name = "InuringPackageProcessingId")
    private int inuringPackageProcessingId;

    @Column(name = "SourceCcy")
    private String sourceCcy;

    @Column(name = "TargetCcy")
    private String targetCcy;

    @Column(name = "ExchangeRate")
    private BigDecimal exchangeRate;

    @Column(name = "StatisticalRateDate")
    private Date statisticalRateDate;

    @Column(name = "StatisticalRateType")
    private String statisticalRateType;

    public InuringPackageProcessingExchangeRate() {
    }
}
