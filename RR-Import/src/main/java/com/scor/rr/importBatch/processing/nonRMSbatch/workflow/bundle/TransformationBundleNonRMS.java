package com.scor.rr.importBatch.processing.nonRMSbatch.workflow.bundle;

/**
 * Created by U005342 on 18/07/2018.
 */
public class TransformationBundleNonRMS {
//
//    private Logger log = LoggerFactory.getLogger(TransformationBundle.class);
//
//    private File file;
//    private ImportFileLossDataHeader header;
//    private List<ImportFilePLTData> datas;
//    private List<PLTLossData> pltLossDataList;
//    private FileImportSourceResult sourceResult;
//
//    private SourceRapMapping sourceRapMapping;
//
//    private RegionPeril regionPeril;
//
//    private RRAnalysis rrAnalysis;
//
//    private String instanceId;
//
//    private List<RmsExchangeRate> rmsExchangeRatesOfRRLT;
//
//    private List<String> modelingOptionsOfRRLT;
//
//    public FileImportSourceResult getSourceResult() {
//        return sourceResult;
//    }
//
//    public void setSourceResult(FileImportSourceResult sourceResult) {
//        this.sourceResult = sourceResult;
//    }
//
//    public File getFile() {
//        return file;
//    }
//
//    public void setFile(File file) {
//        this.file = file;
//    }
//
//    public ImportFileLossDataHeader getHeader() {
//        return header;
//    }
//
//    public void setHeader(ImportFileLossDataHeader header) {
//        this.header = header;
//    }
//
//    public List<ImportFilePLTData> getDatas() {
//        return datas;
//    }
//
//    public void setDatas(List<ImportFilePLTData> datas) {
//        this.datas = datas;
//    }
//
//    public List<PLTLossData> getPltLossDataList() {
//        return pltLossDataList;
//    }
//
//    public void setPltLossDataList(List<PLTLossData> pltLossDataList) {
//        this.pltLossDataList = pltLossDataList;
//    }
//
//    public RegionPeril getRegionPeril() {
//        return regionPeril;
//    }
//
//    public void setRegionPeril(RegionPeril regionPeril) {
//        this.regionPeril = regionPeril;
//    }
//
//    public List<String> getModelingOptionsOfRRLT() {
//        return modelingOptionsOfRRLT;
//    }
//
//    public void setModelingOptionsOfRRLT(List<String> modelingOptionsOfRRLT) {
//        this.modelingOptionsOfRRLT = modelingOptionsOfRRLT;
//    }
//
//    public List<RmsExchangeRate> getRmsExchangeRatesOfRRLT() {
//        return rmsExchangeRatesOfRRLT;
//    }
//
//    public void setRmsExchangeRatesOfRRLT(List<RmsExchangeRate> rmsExchangeRatesOfRRLT) {
//        this.rmsExchangeRatesOfRRLT = rmsExchangeRatesOfRRLT;
//    }
//
//    public String getInstanceId() {
//        return instanceId;
//    }
//
//    public void setInstanceId(String instanceId) {
//        this.instanceId = instanceId;
//    }
//
//    public SourceRapMapping getSourceRapMapping() {
//        return sourceRapMapping;
//    }
//
//    public void setSourceRapMapping(SourceRapMapping sourceRapMapping) {
//        this.sourceRapMapping = sourceRapMapping;
//    }
//
//    public RRAnalysis getRrAnalysis() {
//        return rrAnalysis;
//    }
//
//    public void setRrAnalysis(RRAnalysis rrAnalysis) {
//        this.rrAnalysis = rrAnalysis;
//    }
//
//    @Deprecated // to be gc-ed
//    private RmsAnalysisELT rmsAnalysisELT;
//
//    // to be gc-ed
//    private RmsAnalysisELT conformedAnalysisELT;
//
//    @Deprecated // to be gc-ed
//    private AnalysisELTnBetaFunction analysisELTnBetaFunction; // contains data for binary files
//
//    private Boolean eltError = Boolean.FALSE; // Possible errors during imports
//
//    private String errorMessage;
//
//    private List<PLTBundleNonRMS> pltBundles;
//
//    private Double minLayerAtt; // For building ELT convert function // TODO - put down to each ELTLossnBetaFunction
//
//    private String projectImportLogAnalysisId;
//
//    public RmsAnalysisELT getConformedAnalysisELT() {
//        return conformedAnalysisELT;
//    }
//
//    public void setConformedAnalysisELT(RmsAnalysisELT conformedAnalysisELT) {
//        this.conformedAnalysisELT = conformedAnalysisELT;
//    }
//
//    public void setPltBundles(List<PLTBundleNonRMS> pltBundles) {
//        this.pltBundles = pltBundles;
//    }
//
//    public List<PLTBundleNonRMS> getPltBundles() {
//        return pltBundles;
//    }
//
//    public synchronized void addPLTBundle(PLTBundleNonRMS pltBundle) {
//        if (this.pltBundles == null) {
//            this.pltBundles = new ArrayList<>();
//        }
//        this.pltBundles.add(pltBundle);
//    }
//
//    public AnalysisELTnBetaFunction getAnalysisELTnBetaFunction() {
//        return analysisELTnBetaFunction;
//    }
//
//    public void setAnalysisELTnBetaFunction(AnalysisELTnBetaFunction analysisELTnBetaFunction) {
//        this.analysisELTnBetaFunction = analysisELTnBetaFunction;
//    }
//
//    public Double getMinLayerAtt() {
//        return minLayerAtt;
//    }
//
//    public void setMinLayerAtt(Double minLayerAtt) {
//        this.minLayerAtt = minLayerAtt;
//    }
//
//    public void updateMinLayerAtt() {
//        if (minLayerAtt == null) {
//            throw new IllegalArgumentException();
//        }
//        log.info("updateMinLayerAtt = {} to {} EltLosses", minLayerAtt, analysisELTnBetaFunction.getEltLosses().size() );
//        for (com.scor.almf.ihub.treaty.processing.treaty.loss.ELTLossnBetaFunction ELTLossnBetaFunction : analysisELTnBetaFunction.getEltLosses()) {
//            ELTLossnBetaFunction.setMinLayerAtt(minLayerAtt);
//        }
//    }
//
//    /**
//     * TODO
//     * Take care of these methods. it affects error state of ELT and PLT
//     */
//
//    public void gc() {
////        log.info("gc of {}", sourceELTHeader.getId());
////        sourceResult = null;
////        portfolio = null;
////        sourceELTHeader = null;
////        rmsAnalysis = null;
////        rmsAnalysisELT = null;
////        conformedAnalysisELT = null;
////        analysisELTnBetaFunction = null;
////        eltError = null;
////        pltBundles = null;
////        minLayerAtt = null;
//    }
//
//    // TODO - change shared data, temporarily stored here
//    private Integer truncatedEvents;
//    private Double truncatedAAL;
//    private Double truncatedSD;
//    private Double truncationThreshold;
//    private String truncationCurrency;
//
//    public Integer getTruncatedEvents() {
//        return truncatedEvents;
//    }
//
//    public void setTruncatedEvents(Integer truncatedEvents) {
//        this.truncatedEvents = truncatedEvents;
//    }
//
//    public Double getTruncatedAAL() {
//        return truncatedAAL;
//    }
//
//    public void setTruncatedAAL(Double truncatedAAL) {
//        this.truncatedAAL = truncatedAAL;
//    }
//
//    public Double getTruncatedSD() {
//        return truncatedSD;
//    }
//
//    public void setTruncatedSD(Double truncatedSD) {
//        this.truncatedSD = truncatedSD;
//    }
//
//    public Double getTruncationThreshold() {
//        return truncationThreshold;
//    }
//
//    public void setTruncationThreshold(Double truncationThreshold) {
//        this.truncationThreshold = truncationThreshold;
//    }
//
//    public String getTruncationCurrency() {
//        return truncationCurrency;
//    }
//
//    public void setTruncationCurrency(String truncationCurrency) {
//        this.truncationCurrency = truncationCurrency;
//    }
//
//    public Boolean getEltError() {
//        eltError = rmsAnalysisELT == null || rmsAnalysisELT.getEltLosses() == null || rmsAnalysisELT.getEltLosses().isEmpty() || pltBundles == null || pltBundles.isEmpty();
//        return eltError;
//    }
//
//    public String getErrorMessage() {
//        return errorMessage;
//    }
//
//    public void setErrorMessage(String errorMessage) {
//        this.errorMessage = errorMessage;
//    }
//
//    public String getProjectImportLogAnalysisId() {
//        return projectImportLogAnalysisId;
//    }
//
//    public void setProjectImportLogAnalysisId(String projectImportLogAnalysisId) {
//        this.projectImportLogAnalysisId = projectImportLogAnalysisId;
//    }
}
