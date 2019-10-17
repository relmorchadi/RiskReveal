package com.scor.rr.importBatch.processing.elt;

/**
 * Created by U002629 on 19/02/2015.
 */
public class ELTMerger implements ELTHandler {
    @Override
    public Boolean batchHandle() {
        return true;
    }
}
