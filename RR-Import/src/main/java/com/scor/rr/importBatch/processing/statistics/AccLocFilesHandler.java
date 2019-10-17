package com.scor.rr.importBatch.processing.statistics;

/**
 * Created by U002629 on 19/05/2015.
 */
public interface AccLocFilesHandler {
    Boolean copyFilesToFW();

    Boolean copyFilesToIhub();

    boolean removeTmpFiles();
}
