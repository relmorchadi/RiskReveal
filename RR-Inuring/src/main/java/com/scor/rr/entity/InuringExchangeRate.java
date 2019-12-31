package com.scor.rr.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
@Data
@Entity
@Table(name = "ZZ_ExchangeRate")
public class InuringExchangeRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ExchangeRateId", nullable = false)
    private int exchangeRateId;

    @Column(name = "Entity")
    private int entity;

    @Column(name = "OldId")
    private String oldId;

    @Column(name = "DomesticCurrency")
    private int domesticCurrency;

    @Column(name = "Type")
    private String type;

    @Column(name = "CAD_Rate")
    private double CAD_Rate;

    @Column(name = "EUR_Rate")
    private double EUR_Rate;

    @Column(name = "GBP_Rate")
    private double GBP_Rate;

    @Column(name = "SGD_Rate")
    private double SGD_Rate;

    @Column(name = "USD_Rate")
    private double USD_Rate;

    @Column(name = "EffectiveDate")
    private Date effectiveDate;

    @Column(name = "LastUpdatedBy")
    private int lastUpdatedBy;

    @Column(name = "IsActive")
    private boolean isActive;

    @Column(name = "CreatedBy")
    private int createdBy;

    @Column(name = "CreateDate")
    private Date createDate;

    @Column(name = "LastUpdateDate")
    private Date lastUpdateDate;
}
