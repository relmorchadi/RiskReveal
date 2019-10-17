package com.scor.rr.importBatch.processing.workflow;

import org.springframework.batch.core.ExitStatus;

/**
 * Created by U002629 on 03/04/2015.
 */
public interface FlowHandler {
    ExitStatus handleInit();

    Boolean handleCompletion();

    Boolean handleError();

    Boolean handleRunning();
}
