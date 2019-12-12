package com.scor.rr.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class EdmPortfolio {

    private Long edmId;
    private String edmName;
    private Long portfolioId;
    private String number;
    private String name;
    private Date created;
    private String description;
    private String type;
    private String peril;
    private String agSource;
    private String agCedant;
    private String agCurrency;
    private BigDecimal tiv;
}
