package com.scor.rr.service;

import com.scor.rr.domain.dto.ImportReferenceData;
import com.scor.rr.domain.enums.TargetCurrencyEnum;
import com.scor.rr.repository.CurrencyRepository;
import com.scor.rr.repository.FinancialPerspectiveRepository;
import com.scor.rr.repository.ModellingSystemInstanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RefDataService {

    @Autowired
    FinancialPerspectiveRepository financialPerspectiveRepository;


    @Autowired
    ModellingSystemInstanceRepository modellingSystemInstanceRepository;

    public ImportReferenceData getImportRefs() {
        return ImportReferenceData
                .builder()
                .currencies(TargetCurrencyEnum.currencyChoices)
                .financialPerspectives(financialPerspectiveRepository.findAllCodesAndDesc())
                .rmsInstances(modellingSystemInstanceRepository.findInstanceCodes())
                .build();
    }

}
