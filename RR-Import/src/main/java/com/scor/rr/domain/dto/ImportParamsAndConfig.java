package com.scor.rr.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class ImportParamsAndConfig {

    private String instanceId;
    private String projectId;
    private String userId;
    private List<ImportSelectionDto> analysisConfig;
    private List<PortfolioSelectionDto> portfolioConfig;

}
