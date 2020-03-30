package com.scor.rr.domain.dto.TargetBuild;

import com.scor.rr.domain.ContractSearchResult;
import com.scor.rr.domain.entities.Project.ProjectCardView;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Data
@EqualsAndHashCode(callSuper = false)
public class TreatyWorkspaceDTO extends WorkspaceStats {

    private String id;
    private Integer uwYear;
    private String workspaceName;
    private String cedantCode;
    private String cedantName;
    private String subsidiaryId;
    private String subsidiaryName;
    private String ledgerName;
    private Date inceptionDate;
    private Date expiryDate;
    private String subsidiaryLedgerId;
    private Boolean isFavorite;
    private Boolean isPinned;
    private String currency;
    private String contractDatasource;
    private String marketChannel;
    private List<String> treatySections;
    private List<Integer> years;
    private List<ProjectCardView> projects;

    public TreatyWorkspaceDTO(ContractSearchResult contract, String marketChannel) {
        super();
        this.id = contract.getId();
        this.uwYear = contract.getUwYear();
        this.workspaceName = contract.getWorkspaceName();
        this.cedantCode = contract.getCedantCode();
        this.cedantName = contract.getCedantName();
        this.subsidiaryId = ofNullable(contract.getSubsidiaryid()).map(String::valueOf).orElse(null);
        this.subsidiaryName = contract.getSubsidiaryName();
        this.ledgerName = contract.getSubsidiaryLedgerName();
        this.inceptionDate = contract.getInceptionDate();
        this.expiryDate = contract.getExpiryDate();
        this.subsidiaryLedgerId = contract.getSubsidiaryLedgerid();
        this.contractDatasource = contract.getContractSourceTypeName();
        this.marketChannel = marketChannel;
        this.currency = contract.getLiabilityCurrencyid();
    }

    public void setTreatySections(List<ContractSearchResult> items) {
        this.treatySections = items.stream().filter(Objects::nonNull).map(item -> ofNullable(item.getSectionLabel()).map(sectLabel -> sectLabel.concat(" ").concat(item.getTreatyid().concat("/ ").concat(String.valueOf(item.getSectionid())))).orElse(item.getTreatyid().concat("/ ").concat(String.valueOf(item.getSectionid())))
        ).distinct().collect(Collectors.toList());
    }

    public void setYear(int year) {
        this.years = Arrays.asList(year);
    }

    public void setProjects(List<ProjectCardView> projects) {
        if(projects != null) {
            this.projects = projects;
        } else {
            this.projects= new ArrayList<>();
        }
    }


}
