package com.scor.rr.domain.entities.references.omega;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

/**
 * The persistent class for the Currency database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "Currency")
@Data
public class Currency {
    @Id
    @Column(name = "CurrencyId")
    private String currencyId;
    @Column(name = "Code")
    private String code;
    @Column(name = "CountryCode")
    private String countryCode;
    @Column(name = "ExpiryDate")
    private Date expiryDate;
    @Column(name = "InceptionDate")
    private Date inceptionDate;
    @Column(name = "ReportingCurrencyCode")
    private String reportingCurrencyCode;
    @Column(name = "Label")
    private String label;
    @Column(name = "IsReportingCurrency")
    private Boolean isReportingCurrency;

    public Currency() {
    }

    public Currency(String currencyId, String code) {
        // @formatter:off
        this.currencyId = currencyId;
        this.code = code;
        // @formatter:on
    }
}
