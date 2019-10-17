package com.scor.rr.importBatch.processing.batch;

/**
 * Created by U002629 on 26/06/2015.
 */
public interface ALMFBarrier {
    Boolean acquire() throws InterruptedException;

    Boolean release();
}
