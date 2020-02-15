package com.scor.rr.domain.dto;

import com.scor.rr.domain.riskLink.RLPortfolioSelection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RLPortfolioSelectionDtoWithPortfolioInfo {

    private String portfolioName;
    private String portfolioNumber;
    private Long rlId;
    private String sourceCurrency;
    private String targetCurrency;
    private Double unitMultiplier;
    private Double proportion;
    private boolean importLocationLevel;
    private Long rlPortfolioId;
    private List<Integer> divisions;
    private Long projectId;
    private Long edmId;
    private String edmName;

    public RLPortfolioSelectionDtoWithPortfolioInfo(RLPortfolioSelection element) {
        this.rlId = element.getRlPortfolio().getRlId();
        this.portfolioName = element.getRlPortfolio().getName();
        this.portfolioNumber = element.getRlPortfolio().getNumber();
        this.sourceCurrency = element.getRlPortfolio().getAgCurrency();
        this.projectId = element.getProjectId();
        this.rlPortfolioId = element.getRlPortfolio().getRlPortfolioId();
        this.targetCurrency = element.getTargetCurrency();
        this.unitMultiplier = element.getUnitMultiplier();
        this.proportion = element.getProportion();
        this.divisions = new ArrayList<>();
        this.edmId= element.getRlPortfolio().getEdmId();
        this.edmName= element.getRlPortfolio().getEdmName();
        if (element.getDivision() != null)
            this.divisions.add(element.getDivision());
    }

    public void addToDivisionList(Integer division) {
        this.divisions.add(division);
    }
}
