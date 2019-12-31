package com.scor.rr.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioSelectionDto {

    private String targetCurrency;
    private Double unitMultiplier;
    private Double proportion;
    private boolean importLocationLevel;
    //private String analysisRegions;
    private Long rlPortfolioId;
    private Long projectId;
}
