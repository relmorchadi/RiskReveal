package com.scor.rr.importBatch.processing.batch;

import com.scor.rr.domain.entities.cat.CATRequest;
import com.scor.rr.domain.entities.references.cat.ProcessStatus;
import com.scor.rr.domain.enums.ProcessStatuses;
import com.scor.rr.importBatch.processing.domain.MessageData;


/**
 * Created by U002629 on 07/04/2015.
 */
public interface BaseBatchBean {

    CATRequest loadRequest();

    CATRequest loadRequestFromDB();

    CATRequest loadStatusFromDB();

    boolean checkRunning();

    void removeRunning();

    void persisRequest();

    void persistCATObjectGroup();

    void persisRequest(CATRequest request);

    void invalidateRequest();

    String getFormattedDate();

    void setMessages(MessageData errors);

    void writeMessage(String message, String statusCode, String process);

    void notify(ProcessStatuses status, ProcessStatuses process);
}
