package com.scor.rr.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class ImportParamsAndConfig {

    private String instanceId;
    private String projectId;
    private String userId;
    private List<ImportSelectionDto> analysisConfig;
    private List<PortfolioSelectionDto> portfolioConfig;

    public void addAnalysisConfig(ImportSelectionDto analysisConfig) {
        this.analysisConfig.add(analysisConfig);
    }

    public void addPortfolioConfig(PortfolioSelectionDto portfolioConfig) {
        this.portfolioConfig.add(portfolioConfig);
    }
}
