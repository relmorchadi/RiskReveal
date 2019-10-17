package com.scor.rr.importBatch.processing.batch;

import com.scor.rr.domain.entities.cat.CATRequest;
import com.scor.rr.domain.enums.ProcessStatuses;

public interface MessageService {
    void writeMessage(CATRequest request, String entityKey, String entityType, String message, String statusCode, String process);

    void clearMessages(String carId);

    void writeNotification(String catReqId, String division, String periodBasis, ProcessStatuses status, ProcessStatuses process);

    void writeNotification(String catReqId, ProcessStatuses status, ProcessStatuses process);
}
