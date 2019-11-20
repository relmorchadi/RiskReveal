package com.scor.rr.domain.reference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;


@Entity
@Table(name = "Currency")
@Data
@AllArgsConstructor
@NoArgsConstructor
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
}

