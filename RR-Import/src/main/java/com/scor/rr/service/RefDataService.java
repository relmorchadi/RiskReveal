package com.scor.rr.service;

import com.scor.rr.domain.dto.ImportReferenceData;
import com.scor.rr.domain.dto.RmsInstanceDto;
import com.scor.rr.domain.enums.TargetCurrencyEnum;
import com.scor.rr.repository.CurrencyRepository;
import com.scor.rr.repository.FinancialPerspectiveRepository;
import com.scor.rr.repository.ModellingSystemInstanceRepository;
import com.scor.rr.service.abstraction.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

@Component
public class RefDataService {

    @Autowired
    FinancialPerspectiveRepository financialPerspectiveRepository;

    @Autowired
    ModellingSystemInstanceRepository modellingSystemInstanceRepository;

    @Autowired
    private ConfigurationService configurationService;

    public ImportReferenceData getImportRefs(String carId) {
        return ImportReferenceData
                .builder()
                .currencies(TargetCurrencyEnum.currencyChoices)
                .financialPerspectives(financialPerspectiveRepository.findAllCodesAndDesc())
                .rmsInstances(modellingSystemInstanceRepository.findActiveInstances().stream().map(msi -> new RmsInstanceDto(msi)).collect(toList()))
                .division(ofNullable(carId).map(id -> configurationService.getDivisions(id)).orElse(null))
                .build();
    }

}
