package com.scor.rr.domain.dto.TargetBuild;

import com.scor.rr.domain.WorkspaceEntity;
import com.scor.rr.domain.entities.Project.ProjectCardView;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class FacWorkspaceDTO extends WorkspaceStats{
    private String id;
    private String workspaceName;
    private String cedantCode;
    private String cedantName;
    private String currency;
    private String marketChannel;
    private Date expiryDate;
    private Date inceptionDate;

    private List<Integer> years;
    private List<ProjectCardView> projects;

    public FacWorkspaceDTO() {
        super();
    }

    public FacWorkspaceDTO(WorkspaceEntity ws) {
        super();
        this.id = ws.getWorkspaceContextCode();
        this.workspaceName = ws.getWorkspaceName();
        this.cedantCode = ws.getWorkspaceContextCode();
        this.cedantName = ws.getClientName();
        this.marketChannel = ws.getWorkspaceMarketChannel();
        this.inceptionDate = new Date();
        this.expiryDate = new Date();
    }
}
