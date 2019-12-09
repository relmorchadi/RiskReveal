package com.scor.rr.service.abstraction;

import com.scor.rr.domain.dto.RLAnalysisDto;
import com.scor.rr.domain.dto.RLPortfolioDto;

import java.util.List;

public interface ConfigurationService {

    List<RLAnalysisDto> getRLAnalysisByRLModelDataSourceId(Long rlModelDataSourceId);

    List<RLPortfolioDto> getRLPortfolioByRLModelDataSourceId(Long rlModelDataSourceId);
}
