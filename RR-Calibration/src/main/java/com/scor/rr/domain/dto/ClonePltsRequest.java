package com.scor.rr.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class ClonePltsRequest {
    String newProjectName;
    String newProjectDescription;
    Long existingProjectId;
    List<Long> pltIds;
    String cloningType;
    Integer targetWorkspaceUwYear;
    String targetWorkspaceContextCode;
}
