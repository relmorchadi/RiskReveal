package com.scor.rr.domain.dto;

import com.scor.rr.domain.riskLink.RLImportSelection;
import com.scor.rr.domain.riskLink.RLImportTargetRAPSelection;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ImportSelectionDto {

    private Long projectId;
    private Long rlAnalysisId;
    private String targetCurrency;
    private String targetRegionPeril;
    private Float unitMultiplier;
    private Float proportion;
    private String occurrenceBasis;
    private String occurrenceBasisOverrideReason;
    private List<String> targetRAPCodes;
    private List<Integer> divisions;
    private List<String> financialPerspectives;

    public ImportSelectionDto(RLImportSelection element) {
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
