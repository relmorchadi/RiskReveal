package com.scor.rr.domain;

import lombok.Data;

@Data
public class EdmPortfolioBasic {

    private Long edmId;
    private String edmName;
    private Long portfolioId;
    private String number;
    private String name;
    private String created;
    private String description;
    private String type;
    private String peril;
    private String agSource;
    private String agCedant;
    private String agCurrency;
}
