package com.scor.rr.service.abstraction;

import com.scor.rr.domain.dto.RLAnalysisDto;
import com.scor.rr.domain.dto.RLAnalysisToTargetRAPDto;
import com.scor.rr.domain.dto.RLPortfolioDto;
import com.scor.rr.domain.dto.RegionPerilDto;
import com.scor.rr.domain.views.RLSourceEpHeaderView;

import java.util.List;
import java.util.Map;

public interface ConfigurationService {

    List<RLAnalysisDto> getRLAnalysisByRLModelDataSourceId(String instanceId, Long projectId, Long rmsId);

    List<RLPortfolioDto> getRLPortfolioByRLModelDataSourceId(String instanceId, Long projectId, Long rmsId);

    List<RLAnalysisToTargetRAPDto> getTargetRapByAnalysisId(Long rlAnalysisId);

    List<RegionPerilDto> getRegionPeril(Long rlAnalysisId);

    List<RLSourceEpHeaderView> getSourceEpHeadersByAnalysis(Long rlAnalysisId);

    Map<Long,List<RegionPerilDto>> getRegionPerilForMultiAnalysis(List<Long> rlAnalysisIds);
}
