package com.scor.rr.domain.dto;

import lombok.Data;

@Data
public class UserWorkspaceTabsRequest {

    private Long userWorkspaceTabsId;
    private Integer workspaceUwYear;
    private String workspaceContextCode;
    private String userCode;

}
