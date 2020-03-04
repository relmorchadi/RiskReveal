package com.scor.rr.service;

import com.scor.rr.domain.dto.ExposureManagerData;
import com.scor.rr.domain.dto.ExposureManagerDto;
import com.scor.rr.domain.dto.ExposureManagerParamsDto;
import com.scor.rr.domain.dto.ExposureManagerRefDto;
import com.scor.rr.repository.*;
import com.scor.rr.service.abstraction.DivisionService;
import com.scor.rr.service.abstraction.ExposureManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
    private ExposureSummaryDataRepository exposureSummaryDataRepository;

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

    @Override
    public ExposureManagerDto getExposureManagerData(ExposureManagerParamsDto params) {

        ExposureManagerDto exposureManagerDto = new ExposureManagerDto();
        List<ExposureManagerData> data = new ArrayList<>();

        List<Map<String, Object>> values = exposureSummaryDataRepository.getExposureData(params.getProjectId(),
                params.getPortfolioName(), params.getSummaryType(), params.getDivision(), params.getCurrency(), params.getFinancialPerspective(),
                params.getPage(), params.getPageSize());

        if (params.getRequestTotalRow()) {
            Map<String, Object> totalRow = exposureSummaryDataRepository.getTotalRowExposureData(params.getProjectId(),
                    params.getPortfolioName(), params.getSummaryType(), params.getDivision(), params.getCurrency(), params.getFinancialPerspective());

            ExposureManagerData exposureManagerData = new ExposureManagerData();

            exposureManagerData.setAdmin1("TOTAL");
            exposureManagerData.setCountry("TOTAL");
            exposureManagerData.setTotalTiv((BigDecimal) totalRow.get("Unmapped"));
            Map<String, Object> map = new HashMap<>(totalRow);
            map.remove("Unmapped");
            exposureManagerData.setRegionPerils(map);

            exposureManagerDto.setFrozenRow(exposureManagerData);
        }

        if (values != null && !values.isEmpty()) {
            for (Map<String, Object> entry : values) {
                ExposureManagerData exposureManagerData = new ExposureManagerData();

                exposureManagerData.setTotalTiv((BigDecimal) entry.get("Unmapped"));
                exposureManagerData.setCountry((String) entry.get("CountryCode"));
                exposureManagerData.setAdmin1((String) entry.get("Admin1Code"));
                Map<String, Object> map = new HashMap<>(entry);
                map.remove("Unmapped");
                map.remove("CountryCode");
                map.remove("Admin1Code");
                exposureManagerData.setRegionPerils(map);

                if (exposureManagerDto.getColumns() == null)
                    exposureManagerDto.setColumns(new ArrayList<>(map.keySet()));

                data.add(exposureManagerData);
            }

            exposureManagerDto.setData(data);
        }
        return exposureManagerDto;
    }
}
