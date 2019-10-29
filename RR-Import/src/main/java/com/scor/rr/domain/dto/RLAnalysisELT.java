package com.scor.rr.domain.dto;

import com.scor.rr.domain.RlEltLoss;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class RLAnalysisELT {

    private Long rdmId;
    private String rdmName;
    private Long analysisId;
    private Long instanceId;
    private String financialPerspective;
    private List<RlEltLoss> eltLosses;

    public RLAnalysisELT(Long instanceId, Long rdmId, String rdmName, Long analysisId, String financialPerspective, List<RlEltLoss> eltLosses)
    {
        this.rdmId=rdmId;
        this.rdmName=rdmName;
        this.analysisId=analysisId;
        this.instanceId=instanceId;
        this.financialPerspective=financialPerspective;
        this.eltLosses=eltLosses;
    }

}
