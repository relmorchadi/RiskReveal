package com.scor.rr.domain.dto;


import lombok.Data;

import java.util.List;

@Data
public class ImportParamDTO {
    private
    List<AnalysisImportParam> analysisImportParams;
    List<PortfolioImportParam> portfolioImportParams;


    static class AnalysisImportParam{
        Integer analysisId;
        Integer analysisName;
    }
    static class PortfolioImportParam{

    }
}

