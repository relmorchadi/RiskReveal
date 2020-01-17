package com.scor.rr.service;

import com.scor.rr.domain.dto.*;
import com.scor.rr.domain.riskLink.RLModelDataSource;
import com.scor.rr.domain.riskLink.RLSavedDataSource;
import com.scor.rr.domain.views.RLSourceEpHeaderView;
import com.scor.rr.repository.*;
import com.scor.rr.service.abstraction.ConfigurationService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Ayman IKAR
 * @created 19/12/2019
 */
@Service
@Slf4j
public class ConfigurationServiceImpl implements ConfigurationService {

    @Autowired
    private RLAnalysisRepository rlAnalysisRepository;

    @Autowired
    private RLPortfolioRepository rlPortfolioRepository;

    @Autowired
    private RLModelDataSourceRepository rlModelDataSourceRepository;

    @Autowired
    private RLAnalysisToTargetRAPRepository rlAnalysisToTargetRAPRepository;

    @Autowired
    private RLAnalysisToRegionPerilsRepository rlAnalysisToRegionPerilsRepository;

    @Autowired
    private RLSourceEPHeaderViewRepository rlSourceEPHeaderViewRepository;

    @Autowired
    private ProjectConfigurationForeWriterDivisionRepository projectConfigurationForeWriterDivisionRepository;

    @Autowired
    private RLSavedDataSourceRepository rlSavedDataSourceRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<RLAnalysisDto> getRLAnalysisByRLModelDataSourceId(String instanceId, Long projectId, Long rmsId) {
        RLModelDataSource rlModelDataSource = rlModelDataSourceRepository.findByInstanceIdAndProjectIdAndRlId(instanceId, projectId, rmsId);
        if (rlModelDataSource != null) {
            log.debug("Fetching RLAnalysis for rlModelDataSource {}", rlModelDataSource.getRlModelDataSourceId());
            return rlAnalysisRepository.findByRlModelDataSourceId(rlModelDataSource.getRlModelDataSourceId()).stream()
                    .map(rlAnalysis -> modelMapper.map(rlAnalysis, RLAnalysisDto.class)).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @Override
    public List<RLPortfolioDto> getRLPortfolioByRLModelDataSourceId(String instanceId, Long projectId, Long rmsId) {
        RLModelDataSource rlModelDataSource = rlModelDataSourceRepository.findByInstanceIdAndProjectIdAndRlId(instanceId, projectId, rmsId);
        if (rlModelDataSource != null) {
            log.debug("Fetching RLPortfolio for rlModelDataSource {}", rlModelDataSource.getRlModelDataSourceId());
            return rlPortfolioRepository.findByRlModelDataSourceRlModelDataSourceId(rlModelDataSource.getRlModelDataSourceId()).stream()
                    .map(rlPortfolio -> modelMapper.map(rlPortfolio, RLPortfolioDto.class)).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @Override
    public List<RLAnalysisToTargetRAPDto> getTargetRapByAnalysisId(Long rlAnalysisId) {
        return rlAnalysisToTargetRAPRepository.findByRlAnalysisId(rlAnalysisId).stream()
                .map(element -> modelMapper.map(element, RLAnalysisToTargetRAPDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<RegionPerilDto> getRegionPeril(Long rlAnalysisId) {
        return rlAnalysisToRegionPerilsRepository.findByRlModelAnalysisId(rlAnalysisId).stream()
                .map(element -> modelMapper.map(element, RegionPerilDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<RLSourceEpHeaderView> getSourceEpHeadersByAnalysis(Long rlAnalysisId) {
        return rlSourceEPHeaderViewRepository.findByRLAnalysisId(rlAnalysisId);
    }

    // TODO : Review this later
    @Override
    public List<CARDivisionDto> getDivisions(String carId) {
        List<Map<String, Object>> divisions = projectConfigurationForeWriterDivisionRepository.findByCARId(carId);
        List<CARDivisionDto> carDivisions = new ArrayList<>();
        for (Map<String, Object> division : divisions) {
            CARDivisionDto carDivisionDto = new CARDivisionDto();
            carDivisionDto.setCaRequestId((String) division.get("caRequestId"));
            carDivisionDto.setCarStatus((String) division.get("carStatus"));
            carDivisionDto.setContractId((String) division.get("contractId"));
            carDivisionDto.setCurrency((String) division.get("currency"));
            carDivisionDto.setDivisionNumber(Integer.valueOf((String) division.get("divisionNumber")));
            carDivisionDto.setIsPrincipalDivision(Boolean.parseBoolean(String.valueOf(division.get("IsPrincipalDivision"))));
            carDivisionDto.setProjectId(((BigInteger) division.get("projectId")).longValue());
            carDivisionDto.setUwYear((Integer) division.get("uwYear"));
            carDivisionDto.setWorkspaceId(((BigInteger) division.get("workspaceId")).longValue());
            carDivisions.add(carDivisionDto);
        }

        return carDivisions;
    }

    @Override
    public Map<Long, List<RegionPerilDto>> getRegionPerilForMultiAnalysis(List<Long> rlAnalysisIds) {
        Map<Long, List<RegionPerilDto>> result = new HashMap<>();
        rlAnalysisIds.forEach(id -> result.put(id, this.getRegionPeril(id)));
        return result;
    }

    @Override
    @Transactional(transactionManager = "rrTransactionManager")
    public void saveDefaultDataSources(DataSourcesDto dataSourcesDto) {
        rlSavedDataSourceRepository.deleteByProjectIdAndUserIdAndInstanceId(dataSourcesDto.getProjectId(), dataSourcesDto.getUserId(), dataSourcesDto.getInstanceId());

        if (dataSourcesDto.getDataSources() != null && !dataSourcesDto.getDataSources().isEmpty()) {
            dataSourcesDto.getDataSources().forEach(dataSource -> {
                RLSavedDataSource rlSavedDataSource = modelMapper.map(dataSource, RLSavedDataSource.class);

                rlSavedDataSource.setInstanceId(dataSourcesDto.getInstanceId());
                rlSavedDataSource.setProjectId(dataSourcesDto.getProjectId());
                rlSavedDataSource.setUserId(dataSourcesDto.getUserId());

                rlSavedDataSourceRepository.save(rlSavedDataSource);
            });
        }
    }
    @Override
    public List<RLSavedDataSource> getDefaultDataSources(Long projectId, Long userId, String instanceId){
        return rlSavedDataSourceRepository.findByProjectIdAndInstanceIdAndUserId(projectId, instanceId, userId);
    }
}
