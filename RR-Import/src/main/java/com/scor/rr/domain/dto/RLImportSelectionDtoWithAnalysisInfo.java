package com.scor.rr.domain.dto;

import com.scor.rr.domain.riskLink.RLImportSelection;
import com.scor.rr.domain.riskLink.RLImportTargetRAPSelection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RLImportSelectionDtoWithAnalysisInfo {

    private Long rlId;
    private String analysisName;
    private String analysisDescription;
    private Long projectId;
    private Long rlAnalysisId;
    private String sourceCurrency;
    private String targetCurrency;
    private String targetRegionPeril;
    private Float unitMultiplier;
    private Float proportion;
    private String occurrenceBasis;
    private String occurrenceBasisOverrideReason;
    private List<String> targetRAPCodes;
    private List<RLAnalysisToTargetRAPDto> referenceTargetRaps;
    private List<Integer> divisions;
    private List<String> financialPerspectives;

    public RLImportSelectionDtoWithAnalysisInfo(RLImportSelection element) {
        this.rlId = element.getRlAnalysis().getRlId();
        this.analysisName = element.getRlAnalysis().getAnalysisName();
        this.analysisDescription = element.getRlAnalysis().getAnalysisDescription();
        this.sourceCurrency = element.getRlAnalysis().getAnalysisCurrency();
        this.projectId = element.getProjectId();
        this.rlAnalysisId = element.getRlAnalysis().getRlAnalysisId();
        this.targetCurrency = element.getTargetCurrency();
        this.targetRegionPeril = element.getTargetRegionPeril();
        this.unitMultiplier = element.getUnitMultiplier();
        this.proportion = element.getProportion();
        this.occurrenceBasis = element.getOccurrenceBasis();
        this.occurrenceBasisOverrideReason = element.getOccurrenceBasisOverrideReason();
        this.targetRAPCodes = element.getTargetRaps().stream().map(RLImportTargetRAPSelection::getTargetRAPCode).collect(Collectors.toList());
        this.divisions = new ArrayList<>();
        this.financialPerspectives = new ArrayList<>();
        if (element.getDivision() != null)
            this.divisions.add(element.getDivision());
        this.financialPerspectives.add(element.getFinancialPerspective());
    }

    public void addToDivisionList(Integer division) {
        this.divisions.add(division);
    }

    public void addToTargetRapsList(String targetRaps) {
        this.targetRAPCodes.add(targetRaps);
    }

    public void addToFPList(String financialPerspective) {
        this.financialPerspectives.add(financialPerspective);
    }
}
