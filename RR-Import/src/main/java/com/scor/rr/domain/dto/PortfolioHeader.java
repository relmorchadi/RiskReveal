package com.scor.rr.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioHeader {

    private Long portfolioId;
    private String portfolioName;
    private String portfolioType;
    private String currency;
    private Long edmId;
    private String edmName;
}
