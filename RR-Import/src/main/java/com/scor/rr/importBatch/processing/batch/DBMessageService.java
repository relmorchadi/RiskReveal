package com.scor.rr.importBatch.processing.batch;

//import com.scor.almf.cdm.domain.cat.*;

import com.scor.rr.domain.entities.cat.CATRequest;
import com.scor.rr.domain.entities.omega.Contract;
import com.scor.rr.domain.entities.references.cat.Insured;
import com.scor.rr.domain.entities.references.cat.UWAnalysis;
import com.scor.rr.domain.entities.references.cat.message.MessageNotification;
import com.scor.rr.domain.entities.references.cat.message.MessageProcess;
import com.scor.rr.domain.entities.references.cat.message.MessageStatus;
import com.scor.rr.domain.enums.ProcessStatuses;
import com.scor.rr.repository.cat.ProcessStatusRepository;
import com.scor.rr.repository.cat.message.MessageNotificationRepository;
import com.scor.rr.repository.cat.message.MessageProcessRepository;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by U002629 on 21/05/2015.
 */
@Service(value = "messageService")
@Data
public class DBMessageService implements MessageService {
    private static final Logger log = LoggerFactory.getLogger(DBMessageService.class);

    @Autowired
    private MessageNotificationRepository messageNotificationRepository;
    @Autowired
    private MessageProcessRepository messageProcessRepository;
    @Autowired
    private ProcessStatusRepository processStatusRepository;

    private Notifier notifier;

    @Override
    public void writeMessage(CATRequest request, String entityKey, String entityType, String message, String statusCode, String process) {
        MessageStatus messageStatus = new MessageStatus();
        MessageProcess messageProcess = messageProcessRepository.findByProcessName(process);

        if (log.isDebugEnabled())
            log.debug(message);

        messageStatus.setId(statusCode);

        String code = "BATCH-";
        if (messageProcess != null)
            code = messageProcess.getCode();

        MessageNotification notification = new MessageNotification();
        notification.setCatRequestId(request.getCatRequestId());
        notification.setCode(code);

        java.util.Date utilDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

        notification.setCreatedDate(sqlDate);
        notification.setCreatedUser(request.getAssignedTo());
        notification.setDescription(message);
        final Date createdDate = notification.getCreatedDate();
        notification.setEntityData(Long.toString(createdDate.getTime()));
        notification.setEntityKey(entityKey); //pad to 2 characters
        notification.setEntityType(entityType);

        if (request instanceof CATRequest) {
            final UWAnalysis uWanalysis = ((CATRequest) request).getUwAnalysis();
            if (uWanalysis != null) {
                final Contract contract = uWanalysis.getContract();
                if (contract != null) {
                    final Insured insured = contract.getInsured();
                    //String insuredName = Insured.UNKNOWN;
                    String insuredName = "UNKNOWN";
                    if (insured != null)
                        insuredName = insured.getName();
                    notification.setInsuredName(insuredName);
                    notification.setUwYear(contract.getUwYear() + "_" + contract.getUwOrder());
                }
            }
        }
        notification.setMessageNotificationId("MSG-" + request.getCatRequestId() + "_" + createdDate.getTime());
        notification.setMessageStatus(messageStatus); //status
        notification.setMessageProcess(messageProcess); //RMS Consistency Check
        notification.setRead(false);
        messageNotificationRepository.save(notification);
        if (request.getAssignedTo() != null)
            notifier.notifyMessage(request.getAssignedTo().getCode());
    }

    @Override
    public void clearMessages(String carId) {
        messageNotificationRepository.deleteByCatRequestId(carId);
    }

    @Override
    public void writeNotification(String catReqId, String division, String periodBasis, ProcessStatuses status, ProcessStatuses process) {
        //TODO : Check Later
        //processStatusRepository.save(new ProcessStatus(catReqId, division, periodBasis, status, process));
        notifier.notifyEvent(catReqId);
    }

    @Override
    public void writeNotification(String catReqId, ProcessStatuses status, ProcessStatuses process) {
        //TODO : Check Later
        //processStatusRepository.save(new ProcessStatus(catReqId, status, process));
        notifier.notifyEvent(catReqId);
    }
}
