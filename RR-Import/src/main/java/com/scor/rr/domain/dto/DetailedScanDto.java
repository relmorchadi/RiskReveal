package com.scor.rr.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailedScanDto {

    private List<AnalysisHeader> rlAnalysisList;
    private List<PortfolioHeader> rlPortfolioList;
    private Long projectId;
    private String instanceId;
}
