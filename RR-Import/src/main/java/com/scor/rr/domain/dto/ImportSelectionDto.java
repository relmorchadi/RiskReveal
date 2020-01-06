package com.scor.rr.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class ImportSelectionDto {

    private Long projectId;
    private Long rlAnalysisId;
    private String targetCurrency;
    private String targetRegionPeril;
    private Float unitMultiplier;
    private Float proportion;
    private List<String> targetRAPCodes;
    private List<Integer> divisions;
    private List<String> financialPerspectives;
}
