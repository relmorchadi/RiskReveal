package com.scor.rr.service;

import com.scor.rr.domain.dto.ExposureManagerRefDto;
import com.scor.rr.repository.*;
import com.scor.rr.service.abstraction.DivisionService;
import com.scor.rr.service.abstraction.ExposureManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author Ayman IKAR
 * @created 24/02/2020
 */

@Service
public class ExposureManagerServiceImpl implements ExposureManagerService {

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private FinancialPerspectiveRepository financialPerspectiveRepository;

    @Autowired
    private ProjectConfigurationForeWriterDivisionRepository projectConfigurationForeWriterDivisionRepository;

    @Autowired
    private ProjectConfigurationForeWriterRepository projectConfigurationForeWriterRepository;

    @Autowired
    private ExposureViewDefinitionRepository exposureViewDefinitionRepository;

    @Autowired
    private DivisionService divisionService;

    @Autowired
    private ModelPortfolioRepository modelPortfolioRepository;


    @Override
    public ExposureManagerRefDto getRefForExposureManager(Long projectId) {
        ExposureManagerRefDto exposureManagerRefDto = new ExposureManagerRefDto();

        exposureManagerRefDto.setCurrencies(currencyRepository.findAllCurrencies());
        exposureManagerRefDto.setFinancialPerspectives(financialPerspectiveRepository.findSelectableCodes());
        exposureManagerRefDto.setDivisions(divisionService.getDivisions(projectConfigurationForeWriterRepository.findByProjectId(projectId).getCaRequestId()));
        exposureManagerRefDto.setSummariesDefinitions(exposureViewDefinitionRepository.findExposureViewDefinitionsAliases());
        exposureManagerRefDto.setPortfolios(modelPortfolioRepository.findPortfolioNamesByProjectId(projectId));

        return exposureManagerRefDto;
    }
}
