package com.scor.rr.domain.dto;

import lombok.Data;

@Data
public class UserWorkspaceTabsRequest {

    private Long userWorkspaceTabsId;
    private Long workspaceId;
    private String userCode;

}
