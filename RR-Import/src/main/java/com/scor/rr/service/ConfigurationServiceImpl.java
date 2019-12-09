package com.scor.rr.service;

import com.scor.rr.domain.dto.RLAnalysisDto;
import com.scor.rr.domain.dto.RLPortfolioDto;
import com.scor.rr.repository.RLAnalysisRepository;
import com.scor.rr.repository.RLPortfolioRepository;
import com.scor.rr.service.abstraction.ConfigurationService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private ModelMapper modelMapper;

    public List<RLAnalysisDto> getRLAnalysisByRLModelDataSourceId(Long rlModelDataSourceId) {
        log.debug("Fetching RLAnalysis for rlModelDataSource {}", rlModelDataSourceId);
        return rlAnalysisRepository.findByRlModelDataSourceId(rlModelDataSourceId).stream()
                .map(rlAnalysis -> modelMapper.map(rlAnalysis, RLAnalysisDto.class)).collect(Collectors.toList());
    }

    public List<RLPortfolioDto> getRLPortfolioByRLModelDataSourceId(Long rlModelDataSourceId) {
        log.debug("Fetching RLPortfolio for rlModelDataSource {}", rlModelDataSourceId);
        return rlPortfolioRepository.findByRlModelDataSourceRlModelDataSourceId(rlModelDataSourceId).stream()
                .map(rlPortfolio -> modelMapper.map(rlPortfolio, RLPortfolioDto.class)).collect(Collectors.toList());
    }
}
