package com.scor.rr.service.state;

//import com.scor.rr.domain.ModelPortfolio;
import com.scor.rr.domain.RmsExchangeRate;
import com.scor.rr.domain.dto.AnalysisELTnBetaFunction;
import com.scor.rr.domain.dto.PLTBundle;
import com.scor.rr.domain.dto.RLAnalysisELT;
import com.scor.rr.domain.model.EPCurveHeader;
import com.scor.rr.domain.model.LossDataHeader;
import com.scor.rr.domain.model.SummaryStatisticHeader;
import com.scor.rr.domain.reference.RegionPeril;
import com.scor.rr.domain.riskLink.RLAnalysis;
import com.scor.rr.domain.riskLink.RlSourceResult;
import com.scor.rr.domain.riskReveal.RRAnalysis;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TransformationBundle {

    private String financialPerspective;

    private RlSourceResult sourceResult;

    private RLAnalysis rlAnalysis;

    private RegionPeril regionPeril;

    private RRAnalysis rrAnalysis;

    private LossDataHeader sourceRRLT;

    private String instanceId;

    private LossDataHeader conformedRRLT;

    private List<RmsExchangeRate> rmsExchangeRatesOfRRLT;

    private RLAnalysisELT rlAnalysisELT;

    private RLAnalysisELT conformedRlAnalysisELT;

    private List<String> modelingOptionsOfRRLT;

    private String truncationCurrency;

    private Integer truncatedEvents;

    private Double truncatedAAL;

    private Double truncatedSD;

    private Double truncationThreshold;

    @Deprecated // to be gc-ed
    private AnalysisELTnBetaFunction analysisELTnBetaFunction; // contains data for binary files

    private List<PLTBundle> pltBundles;

    List<EPCurveHeader> epCurves;

    List<SummaryStatisticHeader> summaryStatisticHeaders;

    public synchronized void addPLTBundle(PLTBundle pltBundle) {
        if (this.pltBundles == null) {
            this.pltBundles = new ArrayList<>();
        }
        this.pltBundles.add(pltBundle);
    }

}
