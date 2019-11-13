package com.scor.rr.domain.dto.TargetBuild;

import lombok.Data;

@Data
public class WorkspaceToggleRequest {
    String workspaceContextCode;
    Integer workspaceUwYear;
    Integer userId;
}
