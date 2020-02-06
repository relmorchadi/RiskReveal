package com.scor.rr.service.fileBasedImport.batch;

import com.scor.rr.domain.*;
import com.scor.rr.domain.dto.AnalysisELTnBetaFunction;
import com.scor.rr.domain.dto.ELTLossnBetaFunction;
import com.scor.rr.domain.dto.ImportFilePLTData;
import com.scor.rr.domain.dto.PLTLossData;
import com.scor.rr.domain.importfile.FileImportSourceResult;
import com.scor.rr.domain.importfile.ImportFileLossDataHeader;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
@Data
public class TransformationBundleNonRMS {
    private Logger log = LoggerFactory.getLogger(TransformationBundleNonRMS.class);

    private File file;
    private ImportFileLossDataHeader header;
    private List<ImportFilePLTData> datas;
    private List<PLTLossData> pltLossDataList;
    private FileImportSourceResult sourceResult;

    private SourceRapMappingEntity sourceRapMapping;

    private RegionPerilEntity regionPeril;

    private ModelAnalysisEntity rrAnalysis;

    private String instanceId;

    private List<RmsExchangeRate> rmsExchangeRatesOfRRLT;

    private List<String> modelingOptionsOfRRLT;

    List<SummaryStatisticHeaderEntity> summaryStatisticHeaderEntities;

    public FileImportSourceResult getSourceResult() {
        return sourceResult;
    }

    public void setSourceResult(FileImportSourceResult sourceResult) {
        this.sourceResult = sourceResult;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public ImportFileLossDataHeader getHeader() {
        return header;
    }

    public void setHeader(ImportFileLossDataHeader header) {
        this.header = header;
    }

    public List<ImportFilePLTData> getDatas() {
        return datas;
    }

    public void setDatas(List<ImportFilePLTData> datas) {
        this.datas = datas;
    }

    public List<PLTLossData> getPltLossDataList() {
        return pltLossDataList;
    }

    public void setPltLossDataList(List<PLTLossData> pltLossDataList) {
        this.pltLossDataList = pltLossDataList;
    }

    public RegionPerilEntity getRegionPeril() {
        return regionPeril;
    }

    public void setRegionPeril(RegionPerilEntity regionPeril) {
        this.regionPeril = regionPeril;
    }

    public List<String> getModelingOptionsOfRRLT() {
        return modelingOptionsOfRRLT;
    }

    public void setModelingOptionsOfRRLT(List<String> modelingOptionsOfRRLT) {
        this.modelingOptionsOfRRLT = modelingOptionsOfRRLT;
    }

    public List<RmsExchangeRate> getRmsExchangeRatesOfRRLT() {
        return rmsExchangeRatesOfRRLT;
    }

    public void setRmsExchangeRatesOfRRLT(List<RmsExchangeRate> rmsExchangeRatesOfRRLT) {
        this.rmsExchangeRatesOfRRLT = rmsExchangeRatesOfRRLT;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public SourceRapMappingEntity getSourceRapMapping() {
        return sourceRapMapping;
    }

    public void setSourceRapMapping(SourceRapMappingEntity sourceRapMapping) {
        this.sourceRapMapping = sourceRapMapping;
    }

    public ModelAnalysisEntity getRrAnalysis() {
        return rrAnalysis;
    }

    public void setRrAnalysis(ModelAnalysisEntity rrAnalysis) {
        this.rrAnalysis = rrAnalysis;
    }

    @Deprecated // to be gc-ed
    private AnalysisELTnBetaFunction analysisELTnBetaFunction; // contains data for binary files

    private Boolean eltError = Boolean.FALSE; // Possible errors during imports

    private String errorMessage;

    private List<PLTBundleNonRMS> pltBundles;

    private Double minLayerAtt; // For building ELT convert function // TODO - put down to each ELTLossnBetaFunction

    private String projectImportLogAnalysisId;

    public void setPltBundles(List<PLTBundleNonRMS> pltBundles) {
        this.pltBundles = pltBundles;
    }

    public List<PLTBundleNonRMS> getPltBundles() {
        return pltBundles;
    }

    public synchronized void addPLTBundle(PLTBundleNonRMS pltBundle) {
        if (this.pltBundles == null) {
            this.pltBundles = new ArrayList<>();
        }
        this.pltBundles.add(pltBundle);
    }

    public AnalysisELTnBetaFunction getAnalysisELTnBetaFunction() {
        return analysisELTnBetaFunction;
    }

    public void setAnalysisELTnBetaFunction(AnalysisELTnBetaFunction analysisELTnBetaFunction) {
        this.analysisELTnBetaFunction = analysisELTnBetaFunction;
    }

    public Double getMinLayerAtt() {
        return minLayerAtt;
    }

    public void setMinLayerAtt(Double minLayerAtt) {
        this.minLayerAtt = minLayerAtt;
    }

    public void updateMinLayerAtt() {
        if (minLayerAtt == null) {
            throw new IllegalArgumentException();
        }
        log.info("updateMinLayerAtt = {} to {} EltLosses", minLayerAtt, analysisELTnBetaFunction.getEltLosses().size() );
        for (com.scor.rr.domain.dto.ELTLossnBetaFunction ELTLossnBetaFunction : analysisELTnBetaFunction.getEltLosses()) {
            ELTLossnBetaFunction.setMinLayerAtt(minLayerAtt);
        }
    }

    /**
     * TODO
     * Take care of these methods. it affects error state of ELT and PLT
     */

    public void gc() {
//        log.info("gc of {}", sourceELTHeader.getId());
//        sourceResult = null;
//        portfolio = null;
//        sourceELTHeader = null;
//        rmsAnalysis = null;
//        rmsAnalysisELT = null;
//        conformedAnalysisELT = null;
//        analysisELTnBetaFunction = null;
//        eltError = null;
//        pltBundles = null;
//        minLayerAtt = null;
    }

    // TODO - change shared data, temporarily stored here
    private Integer truncatedEvents;
    private Double truncatedAAL;
    private Double truncatedSD;
    private Double truncationThreshold;
    private String truncationCurrency;

    public Integer getTruncatedEvents() {
        return truncatedEvents;
    }

    public void setTruncatedEvents(Integer truncatedEvents) {
        this.truncatedEvents = truncatedEvents;
    }

    public Double getTruncatedAAL() {
        return truncatedAAL;
    }

    public void setTruncatedAAL(Double truncatedAAL) {
        this.truncatedAAL = truncatedAAL;
    }

    public Double getTruncatedSD() {
        return truncatedSD;
    }

    public void setTruncatedSD(Double truncatedSD) {
        this.truncatedSD = truncatedSD;
    }

    public Double getTruncationThreshold() {
        return truncationThreshold;
    }

    public void setTruncationThreshold(Double truncationThreshold) {
        this.truncationThreshold = truncationThreshold;
    }

    public String getTruncationCurrency() {
        return truncationCurrency;
    }

    public void setTruncationCurrency(String truncationCurrency) {
        this.truncationCurrency = truncationCurrency;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getProjectImportLogAnalysisId() {
        return projectImportLogAnalysisId;
    }

    public void setProjectImportLogAnalysisId(String projectImportLogAnalysisId) {
        this.projectImportLogAnalysisId = projectImportLogAnalysisId;
    }
}
