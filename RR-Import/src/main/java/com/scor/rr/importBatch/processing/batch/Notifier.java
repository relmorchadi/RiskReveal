package com.scor.rr.importBatch.processing.batch;

/**
 * Created by U002629 on 01/10/2015.
 */
public interface Notifier {
    void notifyEvent(String carID);
    void notifyMessage(String userID);
    void notifyImport(Long jobId, String rmspicId, String date, Boolean done);
}
