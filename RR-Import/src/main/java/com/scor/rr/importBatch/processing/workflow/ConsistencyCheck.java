package com.scor.rr.importBatch.processing.workflow;

/**
 * Created by U002629 on 30/03/2015.
 */
public interface ConsistencyCheck {
    ConsistencyStatus performCheck();

    String getCheckName();
}
