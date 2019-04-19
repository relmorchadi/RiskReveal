package com.rr.riskreveal.domain.dto;

import com.rr.riskreveal.domain.ContractSearchResult;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

public class WorkspaceDetailsDTO {

    private String worspaceName;
    private String cedantCode;
    private String cedantName;
    private String subsidiaryId;
    private String subsidiaryName;
    private String ledgerName;

    List<String> treatySections;
    List<String> years;

    public WorkspaceDetailsDTO() {
    }

    public WorkspaceDetailsDTO(List<ContractSearchResult> items, List<String> years) {
        ContractSearchResult first = items.get(0);
        worspaceName = first.getWorkspaceName();
        cedantCode = first.getCedantCode();
        cedantName = first.getCedantName();
        subsidiaryId = ofNullable(first.getSubsidiaryid()).map(String::valueOf).orElse(null);
        subsidiaryName = first.getSubsidiaryName();
        ledgerName = first.getSubsidiaryLedgerName();
        treatySections = items.stream().filter(item -> item != null).map(item -> item.getSectionid())
                .map(String::valueOf).distinct().sorted().collect(Collectors.toList());
        this.years= years;
    }

    public String getWorspaneName() {
        return worspaceName;
    }

    public void setWorspaceName(String worspaceName) {
        this.worspaceName = worspaceName;
    }

    public String getCedantCode() {
        return cedantCode;
    }

    public void setCedantCode(String cedantCode) {
        this.cedantCode = cedantCode;
    }

    public String getCedantName() {
        return cedantName;
    }

    public void setCedantName(String cedantName) {
        this.cedantName = cedantName;
    }

    public String getSubsidiaryId() {
        return subsidiaryId;
    }

    public void setSubsidiaryId(String subsidiaryId) {
        this.subsidiaryId = subsidiaryId;
    }

    public String getSubsidiaryName() {
        return subsidiaryName;
    }

    public void setSubsidiaryName(String subsidiaryName) {
        this.subsidiaryName = subsidiaryName;
    }

    public String getLedgerName() {
        return ledgerName;
    }

    public void setLedgerName(String ledgerName) {
        this.ledgerName = ledgerName;
    }

    public List<String> getTreatySections() {
        return treatySections;
    }

    public void setTreatySections(List<String> treatySections) {
        this.treatySections = treatySections;
    }

    public String getWorspaceName() {
        return worspaceName;
    }

    public List<String> getYears() {
        return years;
    }

    public void setYears(List<String> years) {
        this.years = years;
    }
}
