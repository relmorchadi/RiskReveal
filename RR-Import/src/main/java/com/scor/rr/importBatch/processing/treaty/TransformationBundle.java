package com.scor.rr.importBatch.processing.treaty;

import com.scor.rr.domain.entities.ihub.RRLossTable;
import com.scor.rr.domain.entities.ihub.SourceResult;
import com.scor.rr.domain.entities.plt.RRAnalysis;
import com.scor.rr.domain.entities.rap.SourceRapMapping;
import com.scor.rr.domain.entities.references.RegionPeril;
import com.scor.rr.domain.entities.rms.AnalysisFinancialPerspective;
import com.scor.rr.domain.entities.rms.RmsAnalysis;
import com.scor.rr.domain.entities.rms.RmsAnalysisELT;
import com.scor.rr.domain.entities.rms.RmsExchangeRate;
import com.scor.rr.importBatch.processing.treaty.loss.AnalysisELTnBetaFunction;
import com.scor.rr.importBatch.processing.treaty.loss.ELTLossnBetaFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * In each batch processing step for generation of PLT from ELT
 * transformation package holds a list of bundles
 * Each bundle encompasses extracted and processed data through each batch steps
 */
public class TransformationBundle {

    private Logger log = LoggerFactory.getLogger(TransformationBundle.class);

    private AnalysisFinancialPerspective financialPerspective;

    private SourceResult sourceResult;

    private RmsAnalysis rmsAnalysis;

    private SourceRapMapping sourceRapMapping;
    private RegionPeril regionPeril;

    private RRAnalysis rrAnalysis;
    private RRLossTable sourceRRLT;
    private String instanceId;

    private RRLossTable conformedRRLT;

    private List<RmsExchangeRate> rmsExchangeRatesOfRRLT;

    private List<String> modelingOptionsOfRRLT;

    public RegionPeril getRegionPeril() {
        return regionPeril;
    }

    public void setRegionPeril(RegionPeril regionPeril) {
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

    public SourceRapMapping getSourceRapMapping() {
        return sourceRapMapping;
    }

    public void setSourceRapMapping(SourceRapMapping sourceRapMapping) {
        this.sourceRapMapping = sourceRapMapping;
    }

    public RRLossTable getSourceRRLT() {
        return sourceRRLT;
    }

    public void setSourceRRLT(RRLossTable sourceRRLT) {
        this.sourceRRLT = sourceRRLT;
    }

    public RRLossTable getConformedRRLT() {
        return conformedRRLT;
    }

    public void setConformedRRLT(RRLossTable conformedRRLT) {
        this.conformedRRLT = conformedRRLT;
    }

    public RRAnalysis getRrAnalysis() {
        return rrAnalysis;
    }

    public void setRrAnalysis(RRAnalysis rrAnalysis) {
        this.rrAnalysis = rrAnalysis;
    }

    @Deprecated // to be gc-ed
    private RmsAnalysisELT rmsAnalysisELT;

    // to be gc-ed
    private RmsAnalysisELT conformedAnalysisELT;

    // RmsAnalysisELT with convert function
    // ELTLossnBetaFunction instead of RMSELTLoss
    @Deprecated // to be gc-ed
    private AnalysisELTnBetaFunction analysisELTnBetaFunction; // contains data for binary files

    private Boolean eltError = Boolean.FALSE; // Possible errors during imports

    private String errorMessage;

    private List<PLTBundle> pltBundles;

    private Double minLayerAtt; // For building ELT convert function // TODO - put down to each ELTLossnBetaFunction

    private String projectImportLogAnalysisId;

    public RmsAnalysisELT getConformedAnalysisELT() {
        return conformedAnalysisELT;
    }

    public void setConformedAnalysisELT(RmsAnalysisELT conformedAnalysisELT) {
        this.conformedAnalysisELT = conformedAnalysisELT;
    }

//    public ELTHeader getConformedELTHeader() {
//        return conformedELTHeader;
//    }
//
//    public void setConformedELTHeader(ELTHeader conformedELTHeader) {
//        this.conformedELTHeader = conformedELTHeader;
//    }

//    public ELTHeader getSourceELTHeader() {
//        return sourceELTHeader;
//    }
//
//    public void setSourceELTHeader(ELTHeader sourceELTHeader) {
//        this.sourceELTHeader = sourceELTHeader;
//    }

    public SourceResult getSourceResult() {
        return sourceResult;
    }

    public void setSourceResult(SourceResult sourceResult) {
        this.sourceResult = sourceResult;
    }

    public RmsAnalysis getRmsAnalysis() {
        return rmsAnalysis;
    }

    public void setRmsAnalysis(RmsAnalysis rmsAnalysis) {
        this.rmsAnalysis = rmsAnalysis;
    }

    public RmsAnalysisELT getRmsAnalysisELT() {
        return rmsAnalysisELT;
    }

    public void setRmsAnalysisELT(RmsAnalysisELT rmsAnalysisELT) {
        this.rmsAnalysisELT = rmsAnalysisELT;
    }

    public List<PLTBundle> getPltBundles() {
        return pltBundles;
    }

    public synchronized void addPLTBundle(PLTBundle pltBundle) {
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
        for (ELTLossnBetaFunction ELTLossnBetaFunction : analysisELTnBetaFunction.getEltLosses()) {
            ELTLossnBetaFunction.setMinLayerAtt(minLayerAtt);
        }
    }

    public AnalysisFinancialPerspective getFinancialPerspective() {
        return financialPerspective;
    }

    public void setFinancialPerspective(AnalysisFinancialPerspective financialPerspective) {
        this.financialPerspective = financialPerspective;
    }

    /**
     * TODO
     * Take care of these methods. it affects error state of ELT and PLT
     */
    public void gcELTData() {
//        log.info("gcELTData of {}", sourceELTHeader.getId());
//        rmsAnalysisELT = null;
//        analysisELTnBetaFunction = null;
    }

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

    public Boolean getEltError() {
        eltError = rmsAnalysisELT == null || rmsAnalysisELT.getEltLosses() == null || rmsAnalysisELT.getEltLosses().isEmpty() || pltBundles == null || pltBundles.isEmpty();
        return eltError;
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
