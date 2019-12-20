package com.scor.rr.service.abstraction;

import com.scor.rr.domain.dto.RLAnalysisDto;
import com.scor.rr.domain.dto.RLAnalysisToTargetRAPDto;
import com.scor.rr.domain.dto.RLPortfolioDto;
import com.scor.rr.domain.riskLink.RLSourceEpHeader;

import java.util.List;

public interface ConfigurationService {

    List<RLAnalysisDto> getRLAnalysisByRLModelDataSourceId(String instanceId, Long projectId, Long rmsId);

    List<RLPortfolioDto> getRLPortfolioByRLModelDataSourceId(String instanceId, Long projectId, Long rmsId);

    List<RLSourceEpHeader> getSourceEpHeadersForAnalysis(Long analysisId);

    List<RLAnalysisToTargetRAPDto> getTargetRapByAnalysisId(Long rlAnalysisId);
}
