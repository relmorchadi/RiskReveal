package com.scor.rr.domain.dto;

import com.scor.rr.domain.riskLink.RLPortfolioSelection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private List<Integer> divisions;
    private Long projectId;

    public PortfolioSelectionDto(RLPortfolioSelection item) {
        targetCurrency= item.getTargetCurrency();
        unitMultiplier= item.getUnitMultiplier();
        proportion= item.getProportion();
        importLocationLevel= item.isImportLocationLevel();
        rlPortfolioId=item.getRlPortfolio().getRlPortfolioId();
        divisions=item.getDivision()

    }
}
