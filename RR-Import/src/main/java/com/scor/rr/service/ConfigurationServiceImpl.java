package com.scor.rr.service;

import com.scor.rr.domain.dto.RLAnalysisDto;
import com.scor.rr.domain.dto.RLPortfolioDto;
import com.scor.rr.domain.riskLink.RLModelDataSource;
import com.scor.rr.domain.riskLink.RLSourceEpHeader;
import com.scor.rr.repository.RLAnalysisRepository;
import com.scor.rr.repository.RLModelDataSourceRepository;
import com.scor.rr.repository.RLPortfolioRepository;
import com.scor.rr.repository.RLSourceEpHeaderRepository;
import com.scor.rr.service.abstraction.ConfigurationService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    private RLSourceEpHeaderRepository rlSourceEpHeaderRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<RLAnalysisDto> getRLAnalysisByRLModelDataSourceId(String instanceId, Long projectId, Long rmsId) {
        RLModelDataSource rlModelDataSource = rlModelDataSourceRepository.findByInstanceIdAndProjectIdAndRlId(instanceId, projectId, rmsId);
        if (rlModelDataSource != null) {
            log.debug("Fetching RLAnalysis for rlModelDataSource {}", rlModelDataSource.getRlModelDataSourceId());
            return rlAnalysisRepository.findByRlModelDataSourceId(rlModelDataSource.getRlModelDataSourceId()).stream()
                    .map(rlAnalysis -> modelMapper.map(rlAnalysis, RLAnalysisDto.class)).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

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
    public List<RLSourceEpHeader> getSourceEpHeadersForAnalysis(Long rlAnalysisId) {
        return rlSourceEpHeaderRepository.findByRLAnalysisId(rlAnalysisId);
    }

}
