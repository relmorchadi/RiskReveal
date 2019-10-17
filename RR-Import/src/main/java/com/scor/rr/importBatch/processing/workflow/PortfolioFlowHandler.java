package com.scor.rr.importBatch.processing.workflow;

//import com.scor.almf.cdm.domain.cat.*;
//import com.scor.almf.cdm.domain.reference.*;
//import com.scor.almf.cdm.tools.DeepCopy;

import com.scor.rr.domain.entities.cat.CATRequest;
import com.scor.rr.domain.entities.references.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by U002629 on 25/05/2015.
 */
public class PortfolioFlowHandler extends BasicFlowHandler {
    private static final Logger log = LoggerFactory.getLogger(PortfolioFlowHandler.class);

    private Date inForceDate;
    private String period;
    private Long version;
    private String scope;
    private String catAnalyst;
    private String modellingOnly;

    public void setInForceDate(Date inForceDate) {
        this.inForceDate = inForceDate;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public void setCatAnalyst(String catAnalyst) {
        this.catAnalyst = catAnalyst;
    }

    public void setModellingOnly(String modellingOnly) {
        this.modellingOnly = modellingOnly;
    }

//    @Override
//    public ExitStatus handleInit() {
//        if(checkRunning()) {
//            log.error("An extraction is already running for this portfolio");
//            return new ExitStatus("RUNNING", "RUNNING");
//        }
//
//        clearData();
//        log.info("batch starting");
//
//
//        final User one = new User();
//        one.setId(catAnalyst);
//
//        Calendar c = GregorianCalendar.getInstance();
//        c.setTime(inForceDate);
//        Integer year = c.get(Calendar.YEAR);
//        Integer month = c.get(Calendar.MONTH)+1;
//
//        CATPortfolioRequest request = new CATPortfolioRequest();
//        request.setId(catReqId);
//        final Date date = new Date();
//        request.setImportedOn(date);
//        request.setImportedBy(one);
//        request.setAssignedTo(one);
//        request.setReauestedBy(one);
//        request.setRequestCreationDate(date);
//        request.setModellingOnly(Boolean.parseBoolean(modellingOnly));
//        request.setType(CATRequest.CARType.FAC);
//
//        request.setReferencePortfolio(new ReferencePortfolio());
//        request.getReferencePortfolio().setPortfolioScope(new PortfolioScope());
//        request.getReferencePortfolio().getPortfolioScope().setId(scope);
//        request.getReferencePortfolio().setDescription("portfolio " + period + " : " + version);
//        request.getReferencePortfolio().setInForceDate(inForceDate);
//        request.getReferencePortfolio().setIsModellingOnly(false);
//        request.getReferencePortfolio().setPeriodMonth(month);
//        request.getReferencePortfolio().setPeriodYear(year);
//        request.getReferencePortfolio().setVersion(version.intValue());
//
//        request.setStatus(new Status());
//        request.getStatus().setId(Status.STATUS_PREP);
//        persisRequest(request);
//        request.setStatus(loadRequestFromDB().getStatus());
//        clearResults();
//        return ExitStatus.COMPLETED;
//    }
//
//    @Override
//    public Boolean handleCompletion(){
//        try {
//            clearData();
//            log.info("batch completed OK");
//            writeMessage("extraction completed OK", "I", "FAC-FT");
//            final CATRequest request = loadRequest();
//            createDefaultCatAnalysis((CATPortfolioRequest) request);
//            request.setLastUpdateDate(new Date());
//            persisRequest();
//            persistCATObjectGroup();
//            invalidateRequest();
//        }finally {
//            removeRunning();
//        }
//        return true;
//    }
//
//    @Override
//    public Boolean handleError(){
//        try {
//            clearData();
//            log.info("performing cleanup");
//
//            StringBuilder sb = new StringBuilder();
//            sb.append("extraction completed with errors: \n");
//
//            writeMessage("extraction completed with errors", "E", "FAC-FT");
//            final CATRequest request = loadRequest();
//            final Status status = new Status();
//            status.setId(Status.STATUS_PREP);
//            request.setStatus(status);
//            request.setLastUpdateDate(new Date());
//            clearResults();
//            persisRequest();
//            request.setStatus(loadRequestFromDB().getStatus());
//            invalidateRequest();
//        }finally {
//            removeRunning();
//        }
//        return true;
//    }
//
//    @Override
//    public Boolean handleRunning() {
//        addError("PORTFOLIO IMPORT","An extraction is already running for this portfolio");
//        return true;
//    }

//    public void createDefaultCatAnalysis(CATPortfolioRequest currentCar){
//        final String catAnalysisId = CATAnalysis.buildId(catReqId);
//
//        final CATAnalysis analysis =  new CATAnalysis();
//        analysis.setId(catAnalysisId);
//        analysis.setIsDefault(true);
//        analysis.setName(CATAnalysis.DEFAULT);
//        analysis.setDescription("Default CAT Analysis");
//        analysis.setCarID(catReqId);
//
//        final CATObjectGroup currCATObjectGroup = getCatObjectGroup();
//        final CATAnalysisModelResults modelResults = new CATAnalysisModelResults(currCATObjectGroup.getCarID(), currCATObjectGroup.getDivisionNumber(), currCATObjectGroup.getPeriodBasis().getId(), currCATObjectGroup.getVersion(), catAnalysisId);
//        modelResults.setFinancialPerspectiveELT(new FinancialPerspective(currCATObjectGroup.getFinancialPerspectiveELT().getId()));
//        modelResults.setFinancialPerspectiveStats(new FinancialPerspective(currCATObjectGroup.getFinancialPerspectiveStats().getId()));
//        modelResults.setModelDatasource(currCATObjectGroup.getModelDatasource());
//        modelResults.setModellingResultsByRegionPeril((Map<String, ModellingResult>) DeepCopy.copy(currCATObjectGroup.getModellingResultsByRegionPeril()));
//
//        persistCATAnalysis(analysis, modelResults);
//
//        currentCar.setStatus(new Status(Status.STATUS_ANLS, null, null));
//    }
}
