package com.scor.rr.domain.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AnalysisPortfolioDto {
    private List<RLAnalysisDto> rlAnalyses;
    private List<RLPortfolioDto> rlPortfolios;

    public AnalysisPortfolioDto() {
        this.rlAnalyses= new ArrayList<>();
        this.rlPortfolios= new ArrayList<>();
    }
}
