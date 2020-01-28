package com.scor.rr.domain.dto;

import com.scor.rr.domain.riskLink.RLPortfolioSelection;
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
    private String number;
    private Long rlId;
    private Long edmId;
    private String edmName;
    private String name;
    private String description;
    private Date created;
    private String type;
    private String agCurrency;
    private String agCedent;
    private String agSource;
    private String peril;

}
