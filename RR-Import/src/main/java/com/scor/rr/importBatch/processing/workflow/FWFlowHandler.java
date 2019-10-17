package com.scor.rr.importBatch.processing.workflow;

import com.scor.rr.domain.entities.cat.CATRequest;
import com.scor.rr.domain.enums.KPIConstants;
import com.scor.rr.domain.enums.ProcessStatuses;
import com.scor.rr.importBatch.processing.batch.MessageService;
import com.scor.rr.importBatch.processing.batch.RequestCache;
import com.scor.rr.importBatch.processing.domain.FWData;
import com.scor.rr.metrics.BusinessKpiService;
import com.scor.rr.repository.cat.CATRequestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by U002629 on 18/05/2015.
 */
public class FWFlowHandler implements FlowHandler {
    private static final Logger log = LoggerFactory.getLogger(FWFlowHandler.class);
    private String catReqId;
    private String correlationId;
    private FWData fwData;
    private CATRequestRepository catAnalysisRequestRepository;
    private MessageService messageService;
    private RequestCache cache;
    BusinessKpiService businessKpiService;

    public void setCatReqId(String catReqId) {
        this.catReqId = catReqId;
    }

    public void setFwData(FWData fwData) {
        this.fwData = fwData;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public void setCatAnalysisRequestRepository(CATRequestRepository catAnalysisRequestRepository) {
        this.catAnalysisRequestRepository = catAnalysisRequestRepository;
    }

    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }

    public void setCache(RequestCache cache) {
        this.cache = cache;
    }

    public void setBusinessKpiService(BusinessKpiService businessKpiService) {
        this.businessKpiService = businessKpiService;
    }

    void notify(ProcessStatuses status) {
        messageService.writeNotification(catReqId, status, ProcessStatuses.COMPLETE);
    }

        @Override
    public ExitStatus handleInit() {
        businessKpiService.saveStart(KPIConstants.RR_12_FW_Notifications.toString(), correlationId, getKPIExtraInfo());
        if(cache.checkRunning(catReqId, "FW", "NOTIFY")) {
            log.error("An FW extraction is already running for this CAR id, division and period basis combination");
            return new ExitStatus("RUNNING", "RUNNING");
        }
        fwData.setMyRequest(catAnalysisRequestRepository.findById(catReqId).orElse(null));
        notify(ProcessStatuses.RUNNING);
        return ExitStatus.COMPLETED;
    }

    @Override
    public Boolean handleCompletion() {
        catAnalysisRequestRepository.save(fwData.myRequest());
        messageService.clearMessages(fwData.myRequest().getCatRequestId());
        cache.removeRunning(catReqId, "FW", "NOTIFY");
        businessKpiService.saveEnd(KPIConstants.RR_12_FW_Notifications.toString(), correlationId, KPIConstants.STATUS_OK.toString(), getKPIExtraInfo());
        notify(ProcessStatuses.OK);
        return true;
    }

    @Override
    public Boolean handleError() {
        messageService.writeMessage(fwData.myRequest(), "FW", "ForeWriter", "ERROR IN FW", "E", "FW");
        cache.removeRunning(catReqId, "FW", "NOTIFY");
        businessKpiService.saveEnd(KPIConstants.RR_12_FW_Notifications.toString(), correlationId, KPIConstants.STATUS_KO.toString(), getKPIExtraInfo());
        notify(ProcessStatuses.FAILED);
        return true;
    }

    @Override
    public Boolean handleRunning() {
        messageService.writeMessage(fwData.myRequest(), "FW", "ForeWriter", "A FW extraction is already running for this CAR id, division and period basis combination", "E", "FW");
        return true;
    }

    private Map<String, Object> getKPIExtraInfo() {
        Map<String, Object> kpiExtraInfo = new HashMap<>();
        kpiExtraInfo.put("carID", catReqId);
        kpiExtraInfo.put("correlationId", correlationId);
        final CATRequest request = fwData.myRequest();
        if (request !=null && request.getAssignedTo()!=null)
        {
            kpiExtraInfo.put("catAnalyst", request.getAssignedTo().getCode());
        }
        return kpiExtraInfo;
    }
}
