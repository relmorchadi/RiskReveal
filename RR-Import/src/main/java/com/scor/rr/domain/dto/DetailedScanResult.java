package com.scor.rr.domain.dto;

import com.scor.rr.domain.riskLink.RLAnalysis;
import com.scor.rr.domain.riskLink.RLPortfolio;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailedScanResult {
    private List<RLAnalysisDetailedDto> analysis;
    private List<RLPortfolio> portfolios;
}
