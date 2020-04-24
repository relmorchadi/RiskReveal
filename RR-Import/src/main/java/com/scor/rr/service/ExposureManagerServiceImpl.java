package com.scor.rr.service;

import com.scor.rr.domain.ProjectConfigurationForeWriter;
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
import java.util.*;

import static java.util.stream.Collectors.toMap;


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

        exposureManagerRefDto.setCurrencies(Arrays.asList("USD","CAD","GBP","EUR","SGD"));
        exposureManagerRefDto.setFinancialPerspectives(financialPerspectiveRepository.findSelectableCodes());
        ProjectConfigurationForeWriter pcfw = projectConfigurationForeWriterRepository.findByProjectId(projectId);
        exposureManagerRefDto.setDivisions(divisionService.getDivisions(pcfw != null ? pcfw.getCaRequestId() : null));
        exposureManagerRefDto.setSummariesDefinitions(exposureViewDefinitionRepository.findExposureViewDefinitionsAliases());
        exposureManagerRefDto.setPortfolios(modelPortfolioRepository.findPortfolioNamesByProjectId(projectId));

        List<Map<String, Object>> portfolioAndCurrencyByDivision = modelPortfolioRepository.findPortfolioNamesAndCurrencyAndDivisionByProjectId(projectId);
        List<Integer> divisions = modelPortfolioRepository.getDivisionsInProject(projectId);
        Map<Integer, Map<String, String>> portfoliosAndCurrenciesByDivision = new HashMap<>();

        for (Integer division : divisions) {
            Map<String, String> portfolioCurrency = new HashMap<>();
            portfolioAndCurrencyByDivision.stream().filter(e -> e.get("DivisionNumber").equals(division))
                    .forEach(fe -> {
                        portfolioCurrency.put((String) fe.get("ModelPortfolioName"), (String) fe.get("Currency"));
                        portfoliosAndCurrenciesByDivision.put(division, portfolioCurrency);
                    });
        }

        exposureManagerRefDto.setPortfoliosAndCurrenciesByDivision(portfoliosAndCurrenciesByDivision);
        return exposureManagerRefDto;
    }

    @Override
    public ExposureManagerDto getExposureManagerData(ExposureManagerParamsDto params) {

        ExposureManagerDto exposureManagerDto = new ExposureManagerDto();
        List<ExposureManagerData> data = new ArrayList<>();

        List<Map<String, Object>> values = exposureSummaryDataRepository.getExposureData(params.getProjectId(),
                params.getPortfolioName(), params.getSummaryType(), params.getDivision(), params.getCurrency(), params.getFinancialPerspective(),
                params.getPage(), params.getPageSize(), params.getRegionPerilFilter());

        if (params.getRequestTotalRow()) {
            Map<String, Object> totalRow = exposureSummaryDataRepository.getTotalRowExposureData(params.getProjectId(),
                    params.getPortfolioName(), params.getSummaryType(), params.getDivision(), params.getCurrency(), params.getFinancialPerspective());

            ExposureManagerData exposureManagerData = new ExposureManagerData();

            exposureManagerData.setAdmin1("TOTAL");
            exposureManagerData.setCountry("TOTAL");
            exposureManagerData.setTotalTiv((BigDecimal) totalRow.get("Unmapped"));
            Map<String, Object> map = new HashMap<>(totalRow);
            map.remove("Unmapped");
            map.values().removeAll(Collections.singleton(null));
            exposureManagerData.setRegionPerils(map);

            exposureManagerDto.setFrozenRow(exposureManagerData);
            map = map
                    .entrySet()
                    .stream()
                    .sorted(this.valueComparator().reversed())
                    .collect(
                            toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                    LinkedHashMap::new));
            exposureManagerDto.setColumns(new ArrayList<>(map.keySet()));
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

                if (exposureManagerDto.getColumns() == null) {
                    exposureManagerDto.setColumns(new ArrayList<>(map.keySet()));
                }

                map.values().removeAll(Collections.singleton(null));
                exposureManagerData.setRegionPerils(map);

                data.add(exposureManagerData);
            }

            exposureManagerDto.setData(data);
        }
        return exposureManagerDto;
    }

    private Comparator<Map.Entry> valueComparator() {
        return Comparator.comparing(e -> ((BigDecimal) e.getValue()));
    }
}
