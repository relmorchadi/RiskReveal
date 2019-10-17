package com.scor.rr.importBatch.processing.nonRMSbatch.workflow;

/**
 * Created by U005342 on 14/07/2018.
 */
public interface FlowHandler {
    Boolean handleCompletion();
    Boolean handleError();
}
