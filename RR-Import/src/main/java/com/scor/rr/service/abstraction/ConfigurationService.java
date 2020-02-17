package com.scor.rr.service.abstraction;

import com.scor.rr.domain.ProjectImportRunEntity;
import com.scor.rr.domain.dto.*;
import com.scor.rr.domain.enums.ModelDataSourceType;
import com.scor.rr.domain.riskLink.RLPortfolio;
import com.scor.rr.domain.views.RLSourceEpHeaderView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

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

    List<RLDataSourcesDto> getDefaultDataSources(Long userId);

    ProjectImportRunEntity checkIfProjectHasBeenImportedBefore(Long projectId);

    boolean checkIfProjectHasConfigurations(Long projectId);

    boolean checkIfProjectHasScannedDataSources(Long projectId);

    List<RLDataSourcesDto> getDataSourcesWithSelectedAnalysis(Long projectId);

    List<RLImportSelectionDtoWithAnalysisInfo> getRLModelAnalysisConfigs(Long projectId);

    List<RLPortfolioSelectionDtoWithPortfolioInfo> getRLPortfolioConfigs(Long projectId);

    Page<RLAnalysisDto> filterRLAnalysisByRLModelDataSourceId(String instanceId, Long projectId, Long userId, Long rdmId, RLAnalysisDto filter, Pageable pageable);

    List<RLAnalysisDto> filterRLAnalysisByRLModelDataSourceId(String instanceId, Long projectId, Long userId, Long rdmId, RLAnalysisDto filter);

    Page<RLPortfolioDto> filterRLPortfolioByRLModelDataSourceId(String instanceId, Long projectId, Long userId, Long edmId, RLPortfolioDto filter, Pageable pageable);

    List<RLPortfolioDto> filterRLPortfolioByRLModelDataSourceId(String instanceId, Long projectId, Long userId, Long edmId, RLPortfolioDto filter);

    Map<Long, AnalysisPortfolioDto> getAutoAttach(String wsId, List<Long> edmIds,List<Long> rdmIds, List<Long> divisionsIds);

    void deleteRlDataSource(Long rlDataSourceId);
}
