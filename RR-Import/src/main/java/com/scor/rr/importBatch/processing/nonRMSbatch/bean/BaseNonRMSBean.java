package com.scor.rr.importBatch.processing.nonRMSbatch.bean;

import com.scor.rr.domain.entities.cat.CATObjectGroup;
import com.scor.rr.domain.enums.PeriodBasisStatus;
import com.scor.rr.domain.utils.cat.ModellingResult;
import com.scor.rr.domain.utils.plt.PLT;
import com.scor.rr.importBatch.businessrules.IBusinessRulesService;
import com.scor.rr.importBatch.businessrules.bean.BusinessRulesBean;
import com.scor.rr.importBatch.processing.batch.MessageService;
import com.scor.rr.importBatch.processing.batch.RequestCache;
import com.scor.rr.importBatch.processing.domain.MessageData;
import com.scor.rr.importBatch.processing.mapping.MappingHandler;
import com.scor.rr.importBatch.processing.utils.TTPathUtils;
import com.scor.rr.importBatch.processing.ylt.meta.*;
import com.scor.rr.metrics.BusinessKpiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by U005342 on 14/07/2018.
 */
public abstract class BaseNonRMSBean {

    private static final Logger log = LoggerFactory.getLogger(BaseNonRMSBean.class);
    protected String portfolio;
    protected String catReqId;
    private String instanceId;
    protected String fpELT;
    protected String fpStats;
    protected Long version;
    protected MappingHandler mappingHandler;
    private final SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd-HHmmssSSS", Locale.US);
    private RequestCache cache;
    protected MessageData messages;
    private MessageService messageService;
    protected BusinessKpiService businessKpiService;
    private String lob;
    private Boolean construction;
    protected String jobType;
    protected IBusinessRulesService businessRulesService;
    protected String correlationId;

    public String getPortfolio() {
        return portfolio;
    }

    public String getDivision() {
        return division;
    }

    public String getPeriodBasis() {
        return periodBasis;
    }

    public String getCatReqId() {
        return catReqId;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public String getFpELT() {
        return fpELT;
    }

    public String getFpStats() {
        return fpStats;
    }

    public Long getVersion() {
        return version;
    }

    public MappingHandler getMappingHandler() {
        return mappingHandler;
    }

    public SimpleDateFormat getFormat() {
        return format;
    }

    public RequestCache getCache() {
        return cache;
    }

    public MessageData getMessages() {
        return messages;
    }

    public MessageService getMessageService() {
        return messageService;
    }

    public BusinessKpiService getBusinessKpiService() {
        return businessKpiService;
    }

    public void setLob(String lob) {
        this.lob = lob;
    }

    public Boolean getConstruction() {
        return construction;
    }

    public void setConstruction(Boolean construction) {
        this.construction = construction;
    }

    public String getJobType() {
        return jobType;
    }

    public IBusinessRulesService getBusinessRulesService() {
        return businessRulesService;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setPortfolio(String portfolio) {
        this.portfolio = portfolio;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public void setPeriodBasis(String periodBasis) {
        this.periodBasis = periodBasis;
    }

    public void setCatReqId(String catReqId) {
        this.catReqId = catReqId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public void setFpELT(String fpELT) {
        this.fpELT = fpELT;
    }

    public void setFpStats(String fpStats) {
        this.fpStats = fpStats;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public void setMappingHandler(MappingHandler mappingHandler) {
        this.mappingHandler = mappingHandler;
    }

    public void setCache(RequestCache cache) {
        this.cache = cache;
    }

    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }

    public void setBusinessKpiService(BusinessKpiService businessKpiService) {
        this.businessKpiService = businessKpiService;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public void setBusinessRulesService(IBusinessRulesService businessRulesService) {
        this.businessRulesService = businessRulesService;
    }

    public String getPrefixDirectory() {
        return TTPathUtils.getPrefixDirectory(clientName, clientId, contractId, uwYear, projectId);
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    protected synchronized String getFileName(String type, String rp, String fp, String ccy, String model, String sfx, String fileExtension){
        String pb = "FT";
        if (PeriodBasisStatus.Y1.equals(periodBasis))
            pb = "Y1";

        Map<String, Object> inputParameters = new HashMap<>();
        inputParameters.put("regionPeril",rp);
        inputParameters.put("currency",ccy);
        inputParameters.put("fpToUse",fp);
        inputParameters.put("type",type);
        inputParameters.put("subType","");
        inputParameters.put("periodBasisStr",pb);
        inputParameters.put("model",model);
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
            Date date, String regionPeril, String fp, String currency, XLTOT xltot,Integer targetRapId, Integer simulationPeriod, PLTPublishStatus pltPublishStatus, Integer threadNum, // 0 for pure PLT
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

    protected synchronized Path getDir(){
        Map<String, Object> pathElements = fireRules("firstDir", "pathElements");
        String firstDir = (String) pathElements.get("firstDir");
        String[] dirElements = (String[]) pathElements.get("pathElements");
        return Paths.get(firstDir, dirElements);
    }

    protected synchronized Map<String, Object> fireRules(String... ruleNames){
        return fireRules(null, ruleNames);
    }

    protected synchronized Map<String, Object> fireRules(Map<String, Object> inputParameters, String... ruleNames){
        if (inputParameters == null) {
            inputParameters = new HashMap<>();
        }
        inputParameters.put("domain", "TTY");
        inputParameters.put("jobType", jobType);
        inputParameters.put("fpELT", fpELT);
        inputParameters.put("fpStats", fpStats);
        inputParameters.put("division", division);
        inputParameters.put("periodBasis", periodBasis);
        inputParameters.put("instanceId", instanceId);
        inputParameters.put("portfolio", portfolio);
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

    protected void addMessage(String process, String message){
        messages.addMessage(process, message);
    }

    protected void addError(String process, String message){
        messages.addError(process, message);
    }

    private String reinsuranceType;
    private String prefix;
    private String clientName;
    private String clientId;
    private String contractId;
    protected String division;
    private String uwYear;
    private String sourceVendor;
    private String modelSystemVersion;
    protected String periodBasis;
    private Long importSequence;
    private String userId;
    private String projectId;
    private String fileImportSourceResultIds;
    protected Date runDate;
    private String fileExtension;
    private String nonrmspicId;

    private String filePath;
    private Path ihubPath;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Path getIhubPath() {
        return Paths.get(filePath);
    }

    public void setIhubPath(Path ihubPath) {
        this.ihubPath = ihubPath;
    }

    private static  final  String userName = System.getProperty("user.name");
    private static final String REPO = "C:\\Users\\" + userName + "\\Desktop\\non RMS\\ELEMENT";
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

    public static String getUserName() {
        return userName;
    }

    public static String getREPO() {
        return REPO;
    }

    public SimpleDateFormat getSdf() {
        return sdf;
    }

    public void setSdf(SimpleDateFormat sdf) {
        this.sdf = sdf;
    }

    private List<String> fileImportSourceResultIdsInput; // a set

    public String getNonrmspicId() {
        return nonrmspicId;
    }

    public void setNonrmspicId(String nonrmspicId) {
        this.nonrmspicId = nonrmspicId;
    }

    public List<String> getFileImportSourceResultIdsInput() {
        if (getFileImportSourceResultIds().equals("")) {
            return null;
        }
        return Arrays.asList(getFileImportSourceResultIds().split(";"));
    }

    public void setFileImportSourceResultIdsInput(List<String> fileImportSourceResultIdsInput) {
        this.fileImportSourceResultIdsInput = fileImportSourceResultIdsInput;
    }

    public String getReinsuranceType() {
        return reinsuranceType;
    }

    public void setReinsuranceType(String reinsuranceType) {
        this.reinsuranceType = reinsuranceType;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getUwYear() {
        return uwYear;
    }

    public void setUwYear(String uwYear) {
        this.uwYear = uwYear;
    }

    public String getSourceVendor() {
        return sourceVendor;
    }

    public void setSourceVendor(String sourceVendor) {
        this.sourceVendor = sourceVendor;
    }

    public String getModelSystemVersion() {
        return modelSystemVersion;
    }

    public void setModelSystemVersion(String modelSystemVersion) {
        this.modelSystemVersion = modelSystemVersion;
    }

    public Date getRunDate() {
        return runDate;
    }

    public void setRunDate(Date runDate) {
        this.runDate = runDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getFileImportSourceResultIds() {
        return fileImportSourceResultIds;
    }

    public void setFileImportSourceResultIds(String fileImportSourceResultIds) {
        this.fileImportSourceResultIds = fileImportSourceResultIds;
    }

    public Long getImportSequence() {
        return importSequence;
    }

    public void setImportSequence(Long importSequence) {
        this.importSequence = importSequence;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public File makeFullFile(String prefixDirectory, String filename) {
        final Path fullPath = getIhubPath().resolve(prefixDirectory);
        try {
            Files.createDirectories(fullPath);
        } catch (IOException e) {
            log.error("Exception: ", e);
            throw new RuntimeException("error creating paths "+fullPath, e);
        }
        final File parent = fullPath.toFile();

        File file = new File(parent, filename);
        return file;
    }
}
