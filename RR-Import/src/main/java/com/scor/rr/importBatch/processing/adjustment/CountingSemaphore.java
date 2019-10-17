package com.scor.rr.importBatch.processing.adjustment;

/**
 * Created by U002629 on 09/04/2016.
 */
public class CountingSemaphore {
    private static final boolean DBG = false;
    private int nThreads = 0;
    private int nSteps = 0;
    private int nStepsDone = 0;

    private ExecutionFinishListener listener;

    public CountingSemaphore(int nSteps, ExecutionFinishListener listener) {
        if (nSteps == 0) {
            throw new IllegalStateException("Number of steps must be positive");
        }
        this.nSteps = nSteps;
        this.listener = listener;
        if (DBG) System.out.println("Tree size = " + nSteps);
    }

    public synchronized void take() {
        this.nThreads++;
        this.notify();
    }

    public synchronized void release() throws InterruptedException{
        if (this.nThreads == 0) {
            throw new IllegalStateException();
        }
        this.nThreads--;
    }

    public synchronized void updateProgress() {
        nStepsDone++;
        int progress = 100 * nStepsDone / nSteps;
        if (DBG) System.out.println("progress = " + progress);
        listener.onProgressUpdate(progress);
        if (nSteps == nStepsDone) {
            listener.onExecutionFinish();
        }
    }
}
