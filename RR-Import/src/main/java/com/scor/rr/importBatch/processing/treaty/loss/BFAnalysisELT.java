package com.scor.rr.importBatch.processing.treaty.loss;

import java.util.List;

/**
 * Created by u004119 on 03/05/2016.
 */
public class BFAnalysisELT {

    public BFAnalysisELT() {

    }

    private Long rdmId;
    private String rdmName;

    private Long analysisId;
    private String instanceId;
    private String financialPerspective;
    private List<BFELTLoss> eltLosses;

    public BFAnalysisELT(Long rdmId, String rdmName, Long analysisId, String instanceId, String financialPerspective, List<BFELTLoss> eltLosses) {
        super();
        this.rdmId = rdmId;
        this.rdmName = rdmName;
        this.analysisId = analysisId;
        this.instanceId = instanceId;
        this.financialPerspective = financialPerspective;
        this.eltLosses = eltLosses;
    }

    public Long getRdmId() {
        return rdmId;
    }

    public void setRdmId(Long rdmId) {
        this.rdmId = rdmId;
    }

    public String getRdmName() {
        return rdmName;
    }

    public void setRdmName(String rdmName) {
        this.rdmName = rdmName;
    }

    public Long getAnalysisId() {
        return analysisId;
    }

    public void setAnalysisId(Long analysisId) {
        this.analysisId = analysisId;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getFinancialPerspective() {
        return financialPerspective;
    }

    public void setFinancialPerspective(String financialPerspective) {
        this.financialPerspective = financialPerspective;
    }

    public List<BFELTLoss> getEltLosses() {
        return eltLosses;
    }

    public void setEltLosses(List<BFELTLoss> eltLosses) {
        this.eltLosses = eltLosses;
    }
}
