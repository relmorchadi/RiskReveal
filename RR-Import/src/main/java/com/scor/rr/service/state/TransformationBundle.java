package com.scor.rr.service.state;

import com.scor.rr.domain.RmsExchangeRate;
import com.scor.rr.domain.reference.RegionPeril;
import com.scor.rr.domain.riskLink.RLAnalysis;
import com.scor.rr.domain.riskLink.RlSourceResult;
import com.scor.rr.domain.riskReveal.RRAnalysis;
import com.scor.rr.domain.riskReveal.RRLossTableHeader;
import lombok.Data;

import java.util.List;

@Data
public class TransformationBundle {

    private String financialPerspective;

    private RlSourceResult sourceResult;

    private RLAnalysis rmsAnalysis;

//    private SourceRapMapping sourceRapMapping;

    private RegionPeril regionPeril;

    private RRAnalysis rrAnalysis;

    private RRLossTableHeader sourceRRLT;

    private String instanceId;

    private RRLossTableHeader conformedRRLT;

    private List<RmsExchangeRate> rmsExchangeRatesOfRRLT;

}
