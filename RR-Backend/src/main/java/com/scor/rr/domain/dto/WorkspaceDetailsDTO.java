package com.scor.rr.domain.dto;

import com.scor.rr.domain.ContractSearchResult;
import com.scor.rr.domain.TargetBuild.Project.Project;
import com.scor.rr.domain.TargetBuild.Project.ProjectCardView;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private List<String> treatySections;
    private List<Integer> years;
    private List<ProjectCardView> projects;


    public WorkspaceDetailsDTO(ContractSearchResult first, List<ContractSearchResult> items, List<Integer> years, List<ProjectCardView> projects, Boolean isFavorite, Boolean isPinned) {
        this.id = first.getId();
        this.workspaceName = first.getWorkspaceName();
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
        this.contractDatasource= first.getContractSourceTypeName();
        this.projects= projects;
        this.isFavorite = isFavorite;
        this.isPinned = isPinned;
    }

}
