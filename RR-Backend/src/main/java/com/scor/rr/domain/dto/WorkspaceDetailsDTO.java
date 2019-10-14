package com.scor.rr.domain.dto;

import com.scor.rr.domain.Project;
import com.scor.rr.domain.Workspace;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

public class WorkspaceDetailsDTO {

    private Long id;
    private String workspaceName;
    private String cedantName;
    private String subsidiaryId;
    private String subsidiaryName;
    private String ledgerName;
    private Date inceptionDate;
    private Date expiryDate;
    private String subsidiaryLedgerId;
    private List<String> treatySections;
    private List<Integer> years;
    private List<Project> projects;


    public WorkspaceDetailsDTO(Workspace workspace, List<Integer> years) {
        this.id = workspace.getWorkspaceId();
        this.workspaceName = workspace.getWorkspaceName();
        this.cedantName = workspace.getCedantName();
        this.subsidiaryId = null; // @TODO
        this.subsidiaryName = null; // @TODO
        this.ledgerName = null; //@TODO
        this.inceptionDate = null; //@TODO
        this.expiryDate = null; //@TODO
        this.subsidiaryLedgerId = null; //@TODO
        this.treatySections = null; //@TODO
        this.years = years; //@TODO
        this.projects = ofNullable(workspace.getProjects())
                .map(p -> p.stream().sorted((p1, p2) -> Boolean.compare(p1.getLinkFlag(), Boolean.TRUE)).collect(toList()))
                .orElse(null);
    }

}
