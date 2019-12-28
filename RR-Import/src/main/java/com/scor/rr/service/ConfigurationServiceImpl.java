package com.scor.rr.service;

import com.scor.rr.domain.dto.RLAnalysisDto;
import com.scor.rr.domain.dto.RLAnalysisToTargetRAPDto;
import com.scor.rr.domain.dto.RLPortfolioDto;
import com.scor.rr.domain.dto.RegionPerilDto;
import com.scor.rr.domain.riskLink.RLModelDataSource;
import com.scor.rr.domain.riskLink.RLSourceEpHeader;
import com.scor.rr.domain.views.RLSourceEpHeaderView;
import com.scor.rr.repository.*;
import com.scor.rr.service.abstraction.ConfigurationService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
}
