package com.scor.rr.domain.dto;

import com.scor.rr.domain.entities.ContractSearchResult;
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
    private String marketChannel;
    private List<String> treatySections;
    private List<Integer> years;
    private List<ProjectCardView> projects;
    private List<String> expectedRegionPerils;
    private int publishedForAccumulationPlts;
    private int publishedForPricingPlts;
    private int pricedPlts;


    public WorkspaceDetailsDTO(ContractSearchResult contract, String marketChannel) {
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
            this.publishedForAccumulationPlts = projects.stream().mapToInt(ProjectCardView::getAccumulatedPlts).sum();
            this.publishedForPricingPlts = projects.stream().mapToInt(ProjectCardView::getPublishedForPricingCount).sum();
            this.pricedPlts = projects.stream().mapToInt(ProjectCardView::getFinalPricing).sum();
        }else{
            this.projects= new ArrayList<>();
            this.publishedForAccumulationPlts = 0;
            this.publishedForPricingPlts = 0;
            this.pricedPlts = 0;
        }

    }
}
