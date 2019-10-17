package com.scor.rr.importBatch.processing.workflow;

import com.scor.rr.domain.enums.PeriodBasisStatus;
import com.scor.rr.importBatch.processing.batch.BaseBatchBeanImpl;
import org.springframework.batch.core.ExitStatus;

/**
 * Created by U002629 on 18/05/2015.
 */
public class FwExtractCheckStep extends BaseBatchBeanImpl implements CheckStep {
    @Override
    public ExitStatus runChecks() {
        if(periodBasis.equals(PeriodBasisStatus.FULL))
            return new ExitStatus("FW", "FW");
        else
            return ExitStatus.COMPLETED;
    }
}
