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

    public Long getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(Long portfolioId) {
        this.portfolioId = portfolioId;
    }

    public String getPortfolioType() {
        return portfolioType;
    }

    public void setPortfolioType(String portfolioType) {
        this.portfolioType = portfolioType;
    }

    public String getAnalysisRegion() {
        return analysisRegion;
    }

    public void setAnalysisRegion(String analysisRegion) {
        this.analysisRegion = analysisRegion;
    }

    public String getAnalysisRegionDesc() {
        return analysisRegionDesc;
    }

    public void setAnalysisRegionDesc(String analysisRegionDesc) {
        this.analysisRegionDesc = analysisRegionDesc;
    }

    public String getPeril() {
        return peril;
    }

    public void setPeril(String peril) {
        this.peril = peril;
    }

    public double getTotalTiv() {
        return totalTiv;
    }

    public void setTotalTiv(double totalTiv) {
        this.totalTiv = totalTiv;
    }

    public int getLocCount() {
        return locCount;
    }

    public void setLocCount(int locCount) {
        this.locCount = locCount;
    }
}
