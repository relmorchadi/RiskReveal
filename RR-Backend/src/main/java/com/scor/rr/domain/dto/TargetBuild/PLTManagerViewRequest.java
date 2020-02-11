package com.scor.rr.domain.dto.TargetBuild;

import lombok.Data;

@Data
public class PLTManagerViewRequest {
    String workspaceContextCode;
    Integer workspaceUwYear;
    Integer entity;
    Integer pageNumber;
    Integer pageSize;
    String selectionList;
    Boolean sortSelectedFirst;
    String sortSelectedAction;
}
