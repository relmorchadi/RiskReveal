package com.scor.rr.importBatch.processing.workflow;

import org.springframework.batch.core.ExitStatus;

/**
 * Created by U002629 on 30/03/2015.
 */
public interface CheckStep {
    ExitStatus runChecks();
}
