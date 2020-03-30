package com.scor.rr.service.batch.abstraction;

import com.scor.rr.domain.CurrencyEntity;
import com.scor.rr.domain.LossDataHeaderEntity;
import com.scor.rr.domain.ModelAnalysisEntity;
import com.scor.rr.domain.RegionPerilEntity;
import com.scor.rr.domain.riskLink.RLImportSelection;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.repeat.RepeatStatus;

public interface RegionPerilExtractorInterface {

    RepeatStatus loadRegionPerilAndCreateRRAnalysisAndRRLossTableHeader();

    RegionPerilEntity getRegionPeril(RLImportSelection sourceResult);

    LossDataHeaderEntity makeSourceRRLT(ModelAnalysisEntity modelAnalysisEntity, RLImportSelection sourceResult, String financialPerspective, CurrencyEntity analysisCurrencyEntity);

    LossDataHeaderEntity makeConformedRRLT(ModelAnalysisEntity modelAnalysisEntity, LossDataHeaderEntity sourceRRLT, CurrencyEntity currencyEntity);

}
