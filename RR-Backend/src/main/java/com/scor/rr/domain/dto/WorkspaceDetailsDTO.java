package com.scor.rr.domain.dto;

import com.scor.rr.domain.ContractSearchResult;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

public class WorkspaceDetailsDTO {

    private String worspaceName;
    private String cedantCode;
    private String cedantName;
    private String subsidiaryId;
    private String subsidiaryName;
    private String ledgerName;
    private Date inceptionDate;
    private Date expiryDate;
    private String subsidiaryLedgerId;
    List<String> treatySections;
    List<String> years;

    public WorkspaceDetailsDTO() {
    }

    public WorkspaceDetailsDTO(List<ContractSearchResult> items, List<String> years) {
        ContractSearchResult first = items.get(0);
        this.worspaceName = first.getWorkspaceName();
        this.cedantCode = first.getCedantCode();
        this.cedantName = first.getCedantName();
        this.subsidiaryId = ofNullable(first.getSubsidiaryid()).map(String::valueOf).orElse(null);
        this.subsidiaryName = first.getSubsidiaryName();
        this.ledgerName = first.getSubsidiaryLedgerName();
        this.treatySections = items.stream().filter(Objects::nonNull).map(item -> ofNullable(item.getSectionLabel()).map(sectLabel -> sectLabel.concat(" ").concat(item.getTreatyid().concat("/ ").concat(String.valueOf(item.getSectionid())) ) ).orElse(item.getTreatyid().concat("/ ").concat(String.valueOf(item.getSectionid()))  )
        ).distinct().collect(Collectors.toList());
        this.years= years;
        this.inceptionDate= first.getInceptionDate();
        this.expiryDate = first.getExpiryDate();
        this.subsidiaryLedgerId= first.getSubsidiaryLedgerid();
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

    public Date getInceptionDate() {
        return inceptionDate;
    }

    public void setInceptionDate(Date inceptionDate) {
        this.inceptionDate = inceptionDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getSubsidiaryLedgerId() {
        return subsidiaryLedgerId;
    }

    public void setSubsidiaryLedgerId(String subsidiaryLedgerId) {
        this.subsidiaryLedgerId = subsidiaryLedgerId;
    }
}
