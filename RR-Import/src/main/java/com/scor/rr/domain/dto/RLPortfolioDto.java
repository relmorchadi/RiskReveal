package com.scor.rr.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RLPortfolioDto {

    private Long rlPortfolioId;
    private Long rlId;
    private String name;
    private String description;
    private Date created;
    private String type;
    private String agCurrency;
    private String agCedent;
    private String agSource;
    private String peril;
}
