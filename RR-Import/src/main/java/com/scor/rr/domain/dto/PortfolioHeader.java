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
    private Long edmId;
    private String edmName;
}
