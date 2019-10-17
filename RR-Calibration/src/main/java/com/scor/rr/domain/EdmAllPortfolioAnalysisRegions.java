package com.scor.rr.domain;

import lombok.Data;

@Data
public class EdmAllPortfolioAnalysisRegions {

    private Long portfolioId;
    private String portfolioType;
    private String analysisRegion;
    private String analysisRegionDesc;
    private String peril;
    private double totalTiv;
    private int locCount;

}
