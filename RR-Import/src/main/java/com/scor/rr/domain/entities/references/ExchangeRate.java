package com.scor.rr.domain.entities.references;

import com.scor.rr.domain.entities.references.omega.Currency;
import com.scor.rr.domain.enums.ExchangeRateType;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * The persistent class for the ExchangeRate database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "ExchangeRate")
@Data
public class ExchangeRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ExchangeRateId")
    private Long exchangeRateId;
    @Column(name = "EffectiveDate")
    private Date effectiveDate;
    @Column(name = "CreateDate")
    private Date createDate;
    @Column(name = "LastUpdateDate")
    private Date lastUpdateDate;
    @Column(name = "ExRateAUD")
    private BigDecimal exRateAUD;
    @Column(name = "ExRateCAD")
    private BigDecimal exRateCAD;
    @Column(name = "ExRateEUR")
    private BigDecimal exRateEUR;
    @Column(name = "ExRateGBP")
    private BigDecimal exRateGBP;
    @Column(name = "ExRateJPY")
    private BigDecimal exRateJPY;
    @Column(name = "ExRateNZD")
    private BigDecimal exRateNZD;
    @Column(name = "ExRateSGD")
    private BigDecimal exRateSGD;
    @Column(name = "ExRateUSD")
    private BigDecimal exRateUSD;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CreatedUserId")
    private User createdBy;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LastUpdatedUserId")
    private User lastUpdatedBy;
    @Column(name = "Type")
    private ExchangeRateType type;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CurrencyId")
    private Currency domesticCurrency;

    @Transient
    private Map<String, Double> rates;

    public Double exchangeRateFor(String currency) {
        final Double rate = rates.get(currency);
        if (rate != null)
            return rate;
        return new Double("1.0");
    }

}
