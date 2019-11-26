package com.scor.rr.service.state;

import com.scor.rr.domain.RmsExchangeRate;
import com.scor.rr.domain.dto.AnalysisELTnBetaFunction;
import com.scor.rr.domain.dto.PLTBundle;
import com.scor.rr.domain.dto.RLAnalysisELT;
import com.scor.rr.domain.model.EPCurveHeaderEntity;
import com.scor.rr.domain.model.LossDataHeaderEntity;
import com.scor.rr.domain.model.SummaryStatisticHeaderEntity;
import com.scor.rr.domain.reference.RegionPeril;
import com.scor.rr.domain.riskLink.RLAnalysis;
import com.scor.rr.domain.riskLink.RLImportSelection;
import com.scor.rr.domain.ModelAnalysisEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TransformationBundle {

    private String financialPerspective;

    private RLImportSelection sourceResult;

    private RLAnalysis rlAnalysis;

    private RegionPeril regionPeril;

    private ModelAnalysisEntity modelAnalysisEntity;

    private LossDataHeaderEntity sourceRRLT;

    private String instanceId;

    private LossDataHeaderEntity conformedRRLT;

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

    List<EPCurveHeaderEntity> epCurves;

    List<SummaryStatisticHeaderEntity> summaryStatisticHeaderEntities;

    public synchronized void addPLTBundle(PLTBundle pltBundle) {
        if (this.pltBundles == null) {
            this.pltBundles = new ArrayList<>();
        }
        this.pltBundles.add(pltBundle);
    }

}
