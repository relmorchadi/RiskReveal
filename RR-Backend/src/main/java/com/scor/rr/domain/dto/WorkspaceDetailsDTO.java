package com.scor.rr.domain.dto;

import com.scor.rr.domain.ContractSearchResult;
import com.scor.rr.domain.entities.Project.ProjectCardView;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Data
@NoArgsConstructor
public class WorkspaceDetailsDTO {

    private String id;
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
    private String contractDatasource;
    private Long marketChannel;
    private List<String> treatySections;
    private List<Integer> years;
    private List<ProjectCardView> projects;
    private int expectedRegionPerils;
    private int expectedExposureSummaries;
    private int qualifiedPLTs;
    private int expectedPublishedForPricing;
    private int expectedPriced;
    private int expectedAccumulation;


    public WorkspaceDetailsDTO(ContractSearchResult contract, Long marketChannel) {
        this.id = contract.getId();
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
    }

    public void setTreatySections(List<ContractSearchResult> items) {
        this.treatySections = items.stream().filter(Objects::nonNull).map(item -> ofNullable(item.getSectionLabel()).map(sectLabel -> sectLabel.concat(" ").concat(item.getTreatyid().concat("/ ").concat(String.valueOf(item.getSectionid())))).orElse(item.getTreatyid().concat("/ ").concat(String.valueOf(item.getSectionid())))
        ).distinct().collect(Collectors.toList());
    }

    /**
     * @param projects
     * @Setter project
     * @Desc calculate as well the {publishedForAccumulationPlts, publishedForPricingPlts, pricedPlts} Params from projects
     */
    public void setProjects(List<ProjectCardView> projects) {
        if(projects != null) {
            this.projects = projects;
        }else{
            this.projects= new ArrayList<>();
        }

    }
}
