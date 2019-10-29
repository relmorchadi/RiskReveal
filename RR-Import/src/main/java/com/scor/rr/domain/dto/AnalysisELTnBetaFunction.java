package com.scor.rr.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class AnalysisELTnBetaFunction {

    private Long rdmId;
    private String rdmName;

    private Long analysisId;
    private Long instanceId;
    private String financialPerspective;
    private List<ELTLossnBetaFunction> eltLosses;

    public AnalysisELTnBetaFunction(Long rdmId, String rdmName, Long analysisId, Long instanceId, String financialPerspective, List<ELTLossnBetaFunction> eltLosses) {
        super();
        this.rdmId = rdmId;
        this.rdmName = rdmName;
        this.analysisId = analysisId;
        this.instanceId = instanceId;
        this.financialPerspective = financialPerspective;
        this.eltLosses = eltLosses;
    }
}
