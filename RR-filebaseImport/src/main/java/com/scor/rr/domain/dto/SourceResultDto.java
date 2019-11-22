package com.scor.rr.domain.dto;

import lombok.Data;

@Data
public class SourceResultDto {

    private Integer projectId;
    private Integer rlAnalysisId;
    private String targetCurrency;
    private String targetRegionPeril;
    private Number unitMultiplier;
    private Number proportion;
    private String targetRAPCode;
    private String financialPerspective;
}
