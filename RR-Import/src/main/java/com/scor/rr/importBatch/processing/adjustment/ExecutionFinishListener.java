package com.scor.rr.importBatch.processing.adjustment;

/**
 * Created by U002629 on 09/04/2016.
 */
public interface ExecutionFinishListener {
    public void onExecutionFinish();
    public void onProgressUpdate(int progress);
}