package com.scor.rr.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImportLossDataParams {

    private String projectId;
    private String userId;
    private String instanceId;
    // String contains rlImportSelectionIds separated by ';'
    private String rlImportSelectionIds;
    // String contains rlPortfolioSelectionIds separated by ';'
    private String rlPortfolioSelectionIds;

    public ImportLossDataParams(ImportParamsAndConfig config, List<Long> analysisIds, List<Long> portfolioIds) {
        this.projectId = config.getProjectId();
        this.userId = config.getUserId();
        this.instanceId = config.getInstanceId();
        this.rlImportSelectionIds = ofNullable(analysisIds).map(list -> list.stream().map(String::valueOf).collect(Collectors.joining(";"))).orElse("");
        this.rlPortfolioSelectionIds = ofNullable(portfolioIds).map(list -> list.stream().map(String::valueOf).collect(Collectors.joining(";"))).orElse("");
    }
}
