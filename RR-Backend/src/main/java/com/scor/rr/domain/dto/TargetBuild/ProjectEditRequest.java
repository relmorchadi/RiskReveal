package com.scor.rr.domain.dto.TargetBuild;

import lombok.Data;

@Data
public class ProjectEditRequest {
    private Long projectId;
    private String projectName;
    private String projectDescription;
    private Long assignedTo;
}
