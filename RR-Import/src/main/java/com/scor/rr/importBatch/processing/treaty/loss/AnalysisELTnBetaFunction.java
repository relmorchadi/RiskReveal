package com.scor.rr.importBatch.processing.treaty.loss;

import java.util.List;

/**
 * Created by u004119 on 03/05/2016.
 */
public class AnalysisELTnBetaFunction {

    private Long rdmId;
    private String rdmName;

    private String analysisId;
    private String instanceId;
    private String financialPerspective;
    private List<ELTLossnBetaFunction> eltLosses;

    public AnalysisELTnBetaFunction(Long rdmId, String rdmName, String analysisId, String instanceId, String financialPerspective, List<ELTLossnBetaFunction> eltLosses) {
        super();
        this.rdmId = rdmId;
        this.rdmName = rdmName;
        this.analysisId = analysisId;
        this.instanceId = instanceId;
        this.financialPerspective = financialPerspective;
        this.eltLosses = eltLosses;
    }

    public AnalysisELTnBetaFunction() {

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

    public String getAnalysisId() {
        return analysisId;
    }

    public void setAnalysisId(String analysisId) {
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

    public List<ELTLossnBetaFunction> getEltLosses() {
        return eltLosses;
    }

    public void setEltLosses(List<ELTLossnBetaFunction> eltLosses) {
        this.eltLosses = eltLosses;
    }
}
