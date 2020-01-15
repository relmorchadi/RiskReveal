package com.scor.rr.service.abstraction;

import com.scor.rr.domain.ProjectImportRunEntity;
import com.scor.rr.domain.dto.*;
import com.scor.rr.domain.riskLink.RLSavedDataSource;
import com.scor.rr.domain.views.RLSourceEpHeaderView;

import java.util.List;
import java.util.Map;

public interface ConfigurationService {

    List<RLAnalysisDto> getRLAnalysisByRLModelDataSourceId(String instanceId, Long projectId, Long rmsId);

    List<RLPortfolioDto> getRLPortfolioByRLModelDataSourceId(String instanceId, Long projectId, Long rmsId);

    List<RLAnalysisToTargetRAPDto> getTargetRapByAnalysisId(Long rlAnalysisId);

    List<RegionPerilDto> getRegionPeril(Long rlAnalysisId);

    List<RLSourceEpHeaderView> getSourceEpHeadersByAnalysis(Long rlAnalysisId);

    List<CARDivisionDto> getDivisions(String carId);

    Map<Long, List<RegionPerilDto>> getRegionPerilForMultiAnalysis(List<Long> rlAnalysisIds);

    void saveDefaultDataSources(DataSourcesDto dataSourcesDto);

    List<RLSavedDataSource> getDefaultDataSources(Long projectId, Long userId, String instanceId);

    ProjectImportRunEntity checkIfProjectHasBeenImportedBefore(Long projectId);

    List<RLImportedDataSourcesDto> getDataSourcesWithSelectedAnalysis(Long projectId);

    List<ImportSelectionDto> getRLModelAnalysisConfigs(Long projectId);
}
