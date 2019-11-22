package com.scor.rr.domain.dto;

import lombok.Data;

@Data
public class ImportLossDataParams {

    private String projectId;
    private String userId;
    private String instanceId;
    // String contains rlImportSelectionIds separated by ';'
    private String rlImportSelectionIds;
    // String contains rlPortfolioSelectionIds separated by ';'
    private String rlPortfolioSelectionIds;
}
