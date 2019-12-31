package com.scor.rr.service.batch;

import com.scor.rr.domain.enums.PeriodBasisStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@StepScope
@Service
public class FWExtractionCheck {

    @Value("#{jobParameters['periodBasis']}")
    private String periodBasis;

    public ExitStatus runChecks() {
        if (periodBasis.equals(PeriodBasisStatus.FULL.getCode()))
            return new ExitStatus("FW", "FW");
        else
            return ExitStatus.COMPLETED;
    }
}
