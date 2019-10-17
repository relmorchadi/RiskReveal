package com.scor.rr.importBatch.processing.batch;


import com.scor.rr.domain.entities.cat.CATAnalysis;
import com.scor.rr.domain.entities.cat.CATAnalysisModelResults;
import com.scor.rr.domain.entities.cat.CATObjectGroup;
import com.scor.rr.domain.entities.cat.CATRequest;
import com.scor.rr.domain.enums.PeriodBasisStatus;
import com.scor.rr.domain.enums.ProcessStatuses;
import com.scor.rr.domain.utils.cat.ModellingResult;
import com.scor.rr.domain.utils.plt.PLT;
import com.scor.rr.importBatch.businessrules.IBusinessRulesService;
import com.scor.rr.importBatch.businessrules.bean.BusinessRulesBean;
import com.scor.rr.importBatch.processing.domain.Message;
import com.scor.rr.importBatch.processing.domain.MessageData;
import com.scor.rr.importBatch.processing.mapping.MappingHandler;
import com.scor.rr.importBatch.processing.utils.TTPathUtils;
import com.scor.rr.importBatch.processing.ylt.meta.*;
import com.scor.rr.metrics.BusinessKpiService;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

@Data
@NoArgsConstructor
public abstract class BaseBatchBeanImpl implements BaseBatchBean {

    private static final Logger log = LoggerFactory.getLogger(BaseBatchBeanImpl.class);
    private final SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd-HHmmssSSS", Locale.US);

    protected String portfolio;
    protected String catReqId;
    protected String fpELT;
    protected String fpStats;
    protected Long version;
    protected MessageData messages;
    protected String jobType;

    @Autowired
    protected IBusinessRulesService businessRulesService;
    @Autowired
    private RequestCache cache;
    @Autowired
    private MessageService messageService;
    @Autowired
    protected MappingHandler mappingHandler;
    @Autowired
    protected BusinessKpiService businessKpiService;

    protected String correlationId;
    protected String division;
    protected String periodBasis;
    protected Date runDate;
    private String instanceId;
    private String lob;
    private Boolean construction;
    private String reinsuranceType;
    private String prefix;
    private String clientName;
    private String clientId;
    private String contractId;
    private String uwYear;
    private String sourceVendor;
    private String modelSystemVersion;
    private Long importSequence;
    //    private String pdaId;
    private Long userId;
    private String projectId;
    private String portfolioIds;
    private String sourceResultIds;
    private String fileExtension;
    // new code ri
    private String rmspicId;
    private List<String> portfolioIdsInput; // a set
    private List<String> sourceResultIdsInput; // a set
    private String sourceConfigVendor;

    public static String makeTTFileName(
            String reinsuranceType,
            String prefix,
            String clientName,
            String contractId,
            String division,
            String uwYear,
            XLTAssetType XLTAssetType,
            Date date,
            String sourceVendor,
            String modelSystemVersion,
            String regionPeril,
            String fp,
            String currency,
            String projectId,
            String periodBasis,
            XLTOrigin origin,
            XLTSubType subType,
            XLTOT currencySource,
            Integer targetRapId,
            Integer simulationPeriod,
            PLTPublishStatus pltPublishStatus,
            Integer threadNum, // 0 for pure PLT
            String uniqueId,
            Long importSequence,
            String fileExtension) {
        return TTPathUtils.makeTTFileName(reinsuranceType,
                prefix,
                clientName,
                contractId,
                division,
                uwYear,
                XLTAssetType,
                date,
                sourceVendor,
                modelSystemVersion,
                regionPeril,
                fp,
                currency != null ? currency : null,
                projectId,
                periodBasis,
                origin,
                subType,
                currencySource,
                targetRapId,
                simulationPeriod,
                pltPublishStatus,
                threadNum, // 0 for pure PLT
                uniqueId,
                importSequence,
                null,
                null,
                null,
                fileExtension);
    }

    public String getPrefixDirectory() {
        return TTPathUtils.getPrefixDirectory(clientName, clientId, contractId, uwYear, projectId);
    }

    @Override
    public CATRequest loadRequest() {
        return cache.getRequest(catReqId);
    }

    @Override
    public CATRequest loadRequestFromDB() {
        return cache.getRequestFromDB(catReqId);
    }

    @Override
    public CATRequest loadStatusFromDB() {
        return cache.getStatusFromDB(catReqId);
    }

    @Override
    public boolean checkRunning() {
        return cache.checkRunning(this.catReqId, this.division, this.periodBasis);
    }

    @Override
    public void removeRunning() {
        cache.removeRunning(this.catReqId, this.division, this.periodBasis);
    }

    @Override
    public void persisRequest() {
        cache.persistRequest(catReqId);
    }

    @Override
    public void persistCATObjectGroup() {
        cache.persistCatObjectGroup(catReqId, division, periodBasis);
    }

    @Override
    public void persisRequest(CATRequest request) {
        cache.persistRequest(request);
    }

    @Override
    public void invalidateRequest() {
        cache.removeRequest(catReqId);
    }

    @Override
    public String getFormattedDate() {
        return format.format(runDate);
    }

    @Override
    public void setMessages(MessageData messages) {
        this.messages = messages;
    }

    @Override
    public void notify(ProcessStatuses status, ProcessStatuses process) {
        messageService.writeNotification(catReqId, division, periodBasis, status, process);
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    @Override
    public void writeMessage(String messageStr, String statusCode, String process) {

        StringBuilder sb = new StringBuilder();
        sb.append(messageStr).append("\n");

        final Set<Message> errors = messages.getErrors();
        synchronized (errors) {
            Iterator<Message> i = errors.iterator();
            while (i.hasNext()) {
                Message message = i.next();
                sb.append("error: ").append(message.getProcess()).append(" : ").append(message.getDescription()).append("\n");
            }
        }

        final Set<Message> info = messages.getMessages();
        synchronized (info) {
            Iterator<Message> i = info.iterator();
            while (i.hasNext()) {
                Message message = i.next();
                sb.append("info: ").append(message.getProcess()).append(" : ").append(message.getDescription()).append("\n");
            }
        }

        messageService.writeMessage(loadRequest(), division, portfolio, sb.toString(), statusCode, process);
    }

    protected synchronized String getFileName(String type, String rp, String fp, String ccy, String model, String sfx, String fileExtension) {
        String pb = "FT";
        if (PeriodBasisStatus.Y1.equals(periodBasis))
            pb = "Y1";

        Map<String, Object> inputParameters = new HashMap<>();
        inputParameters.put("regionPeril", rp);
        inputParameters.put("currency", ccy);
        inputParameters.put("fpToUse", fp);
        inputParameters.put("type", type);
        inputParameters.put("subType", "");
        inputParameters.put("periodBasisStr", pb);
        inputParameters.put("model", model);
        inputParameters.put("sfx", sfx);

        Map<String, Object> pathElements = fireRules(inputParameters, "baseFileName", "suffix");
        Object baseFileName = pathElements.get("baseFileName");
        Object suffix = pathElements.get("suffix");
        String identifier = "%s";
        String fileName = new StringBuilder().append(baseFileName).append(suffix).append(identifier).append(fileExtension).toString();
//        String finalName = fileName.trim().replaceAll(" +", " ").replaceAll("[^-a-zA-Z0-9\\s]", "").replaceAll(" ", "-").replaceAll("\\.", "");
        String finalName = fileName.trim().replaceAll(" ", "-");//.replaceAll(" +", " ").replaceAll("[^-a-zA-Z0-9\\s]", "").replaceAll(" ", "-").replaceAll("\\.", "");
        return finalName;
    }

    protected synchronized String makeAPSFileName(
            Date date, String regionPeril, String fp, String currency, XLTOT xltot, String uniqueId, String fileExtension) {
        return makeTTFileName(
                reinsuranceType,
                prefix,
                clientName,
                contractId,
                division,
                uwYear,
                XLTAssetType.ELT,
                date,
                sourceVendor,
                modelSystemVersion,
                regionPeril,
                fp,
                currency,
                projectId,
                periodBasis,
                XLTOrigin.MODEL,
                XLTSubType.APS,
                xltot,
                null,
                null,
                null,
                null,
                uniqueId,
                importSequence,
                fileExtension
        );
    }

    protected synchronized String makeELTFileName(
            Date date, String regionPeril, String fp, String currency, XLTOT xltot, String uniqueId, String fileExtension) {
        return makeTTFileName(
                reinsuranceType,
                prefix,
                clientName,
                contractId,
                division,
                uwYear,
                XLTAssetType.ELT,
                date,
                sourceVendor,
                modelSystemVersion,
                regionPeril,
                fp,
                currency,
                projectId,
                periodBasis,
                XLTOrigin.MODEL,
                XLTSubType.DAT,
                xltot,
                null,
                null,
                null,
                null,
                uniqueId,
                importSequence,
                fileExtension
        );
    }

    protected synchronized String makePLTFileName(
            Date date, String regionPeril, String fp, String currency, XLTOT currencySource, Integer targetRapId, Integer simulationPeriod, PLTPublishStatus pltPublishStatus,
            Integer threadNum, // 0 for pure PLT
            String uniqueId, String fileExtension) {
        return makeTTFileName(
                reinsuranceType,
                prefix,
                clientName,
                contractId,
                division,
                uwYear,
                XLTAssetType.PLT,
                date,
                sourceVendor,
                modelSystemVersion,
                regionPeril,
                fp,
                currency,
                projectId,
                periodBasis,
                XLTOrigin.INTERNAL,
                XLTSubType.DAT,
                currencySource,
                targetRapId,
                simulationPeriod,
                pltPublishStatus,
                threadNum, // 0 for pure PLT
                uniqueId,
                importSequence,
                fileExtension
        );
    }

    protected synchronized String makePLTEPCurveFilename(
            Date date, String regionPeril, String fp, String currency, XLTOT xltot, Integer targetRapId, Integer simulationPeriod, PLTPublishStatus pltPublishStatus, Integer threadNum, // 0 for pure PLT
            String uniqueId, String fileExtension) {
        return makeTTFileName(
                reinsuranceType,
                prefix,
                clientName,
                contractId,
                division,
                uwYear,
                XLTAssetType.PLT,
                date,
                sourceVendor,
                modelSystemVersion,
                regionPeril,
                fp,
                currency,
                projectId,
                periodBasis,
                XLTOrigin.INTERNAL,
                XLTSubType.EPC,
                xltot,
                targetRapId,
                simulationPeriod,
                pltPublishStatus,
                threadNum,
                uniqueId,
                importSequence,
                fileExtension
        );
    }

    protected synchronized String makeELTEPCurveFilename(
            Date date, String regionPeril, String fp, String currency, XLTOT xltot, String uniqueId, String fileExtension) {
        return makeTTFileName(
                reinsuranceType,
                prefix,
                clientName,
                contractId,
                division,
                uwYear,
                XLTAssetType.ELT,
                date,
                sourceVendor,
                modelSystemVersion,
                regionPeril,
                fp,
                currency,
                projectId,
                periodBasis,
                XLTOrigin.MODEL,
                XLTSubType.EPC,
                xltot,
                null,
                null,
                null,
                null,
                uniqueId,
                importSequence,
                fileExtension
        );
    }

    protected synchronized String makePLTSummaryStatFilename(
            Date date, String regionPeril, String fp, String currency, XLTOT xltot, Integer targetRapId, Integer simulationPeriod, PLTPublishStatus pltPublishStatus, Integer threadNum, // 0 for pure PLT
            String uniqueId, String fileExtension) {
        return makeTTFileName(
                reinsuranceType,
                prefix,
                clientName,
                contractId,
                division,
                uwYear,
                XLTAssetType.PLT,
                date,
                sourceVendor,
                modelSystemVersion,
                regionPeril,
                fp,
                currency,
                projectId,
                periodBasis,
                XLTOrigin.INTERNAL,
                XLTSubType.EPS,
                xltot,
                targetRapId,
                simulationPeriod,
                pltPublishStatus,
                threadNum,
                uniqueId,
                importSequence,
                fileExtension
        );
    }

    protected synchronized String makeELTSummaryStatFilename(
            Date date, String regionPeril, String fp, String currency, XLTOT xltot, String uniqueId, String fileExtension) {
        return makeTTFileName(
                reinsuranceType,
                prefix,
                clientName,
                contractId,
                division,
                uwYear,
                XLTAssetType.ELT,
                date,
                sourceVendor,
                modelSystemVersion,
                regionPeril,
                fp,
                currency,
                projectId,
                periodBasis,
                XLTOrigin.MODEL,
                XLTSubType.EPS,
                xltot,
                null,
                null,
                null,
                null,
                uniqueId,
                importSequence,
                fileExtension
        );
    }

    protected synchronized String makeExposureFileName(XLTSubType subType, Date date, String regionPeril, String fp, String currency, XLTOT currencySource, String edmName, String portfolioId, String filenNature, String fileExtension) {
        return TTPathUtils.makeTTFileName(reinsuranceType,
                prefix,
                clientName,
                contractId,
                division,
                uwYear,
                XLTAssetType.EXP,
                date,
                sourceVendor,
                modelSystemVersion,
                regionPeril,
                fp,
                currency,
                null,
                periodBasis,
                XLTOrigin.INTERNAL,
                subType,
                currencySource,
                null,
                null,
                null,
                null,
                null,
                importSequence,
                edmName,
                portfolioId,
                filenNature,
                fileExtension);
    }

    protected synchronized Path getDir() {
        Map<String, Object> pathElements = fireRules("firstDir", "pathElements");
        String firstDir = (String) pathElements.get("firstDir");
        String[] dirElements = (String[]) pathElements.get("pathElements");
        return Paths.get(firstDir, dirElements);
    }

    protected synchronized Map<String, Object> fireRules(String... ruleNames) {
        return fireRules(null, ruleNames);
    }

    protected synchronized Map<String, Object> fireRules(Map<String, Object> inputParameters, String... ruleNames) {
        if (inputParameters == null) {
            inputParameters = new HashMap<>();
        }
        // final CATRequest request = loadRequest();
        inputParameters.put("domain", "TTY");
        inputParameters.put("jobType", jobType);
        inputParameters.put("fpELT", fpELT);
        inputParameters.put("fpStats", fpStats);
        inputParameters.put("division", division);
        inputParameters.put("periodBasis", periodBasis);
        inputParameters.put("instanceId", instanceId);
        inputParameters.put("portfolio", portfolio);
        // inputParameters.put("vendor", ModellingVendor.getVendorFromInstance(request.getModellingSystemInstance()));
        // inputParameters.put("system", ModellingSystem.getSystemFromInstance(request.getModellingSystemInstance()));
        // inputParameters.put("version", ModellingSystemVersion.getVersionFromInstance(request.getModellingSystemInstance()));
        inputParameters.put("extractionDate", runDate);
        inputParameters.put("carID", catReqId);
        // inputParameters.put("catRequest", request);
        BusinessRulesBean businessBean = new BusinessRulesBean();
        businessBean.setInputData(inputParameters);
        for (String ruleName : ruleNames) {
            businessRulesService.runRuleByName(businessBean, ruleName);
        }
        return businessBean.getOutputData();
    }

    protected Map<String, ModellingResult> getModellingResultsByRegionPeril() {
        return getCatObjectGroup().getModellingResultsByRegionPeril();
    }

    protected CATObjectGroup getCatObjectGroup() {
        return cache.getCatObjectGroup(catReqId, division, periodBasis);
    }

    protected PLT getPurePLT(String s) {
        return getModellingResultsByRegionPeril().get(s).getPurePLT();
    }

    protected CATObjectGroup createCatObjectGroup() {
        //TODO : GET PERIODBASIS AND CATREQUEST to pass to the constructor
        //CATObjectGroup catObjectGroup = new CATObjectGroup(catReqId, division, periodBasis, version.intValue());
        CATObjectGroup catObjectGroup = new CATObjectGroup();
        cache.persistCatObjectGroup(catObjectGroup);
        return catObjectGroup;
    }

    protected void clearResults() {
        cache.removeCatObjectGroupFromDB(catReqId, division, periodBasis);
        cache.removeCatAnalysisFromDB(catReqId);
    }

    protected void persistCATAnalysis(CATAnalysis catAnalysis, CATAnalysisModelResults catAnalysisModelResults) {
        cache.persistCatAnalysis(catAnalysis);
        cache.persistCatAnalysisModelResults(catAnalysisModelResults);
    }

    protected void addMessage(String process, String message) {
        messages.addMessage(process, message);
    }

    protected void addError(String process, String message) {
        messages.addError(process, message);
    }

    protected String getLob() {

        if (lob == null) {
            final CATRequest one = loadRequest();
            if (one instanceof CATRequest) {
                CATRequest request = (CATRequest) one;
                lob = request.getUwAnalysis().getDivisions().get(Integer.parseInt(division)).getLob().getCode();
            } else
                lob = "";
        }
        return lob;
    }

    protected boolean isConstruction() {
        if (construction == null)
            construction = "02".equals(getLob());

        return construction;
    }

    protected Map<String, Object> getKPInfo() {
        Map<String, Object> kpiExtraInfo = new HashMap<>();
        kpiExtraInfo.put("carID", catReqId);
        kpiExtraInfo.put("portfolio", portfolio);
        kpiExtraInfo.put("fpStats", fpStats);
        kpiExtraInfo.put("fpELT", fpELT);
        kpiExtraInfo.put("version", version);
        kpiExtraInfo.put("division", division);
        kpiExtraInfo.put("periodBasis", periodBasis);
        kpiExtraInfo.put("instanceId", instanceId);
        kpiExtraInfo.put("correlationId", correlationId);
        final CATRequest catRequest = loadRequest();
        if (catRequest != null && catRequest.getAssignedTo() != null)
            kpiExtraInfo.put("catAnalyst", catRequest.getAssignedTo().getCode());
        //add batch id
        return kpiExtraInfo;
    }

    public String getSourceConfigVendor() {
        return "RMS"; // TODO change it later
//        return sourceConfigVendor;
    }

    public List<String> getPortfolioIdsInput() {
        if (getPortfolioIds().equals("")) {
            return null;
        }
        return Arrays.asList(getPortfolioIds().split(";"));
    }

    public void setPortfolioIdsInput(List<String> portfolioIdsInput) {
        this.portfolioIdsInput = portfolioIdsInput;
    }

    public List<String> getSourceResultIdsInput() { // always not "" or null
        if (getSourceResultIds().equals("")) {
            return null;
        }
        return Arrays.asList(getSourceResultIds().split(";"));
    }
}
