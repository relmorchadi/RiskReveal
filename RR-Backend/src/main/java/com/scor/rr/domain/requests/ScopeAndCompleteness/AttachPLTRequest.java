package com.scor.rr.domain.requests.ScopeAndCompleteness;

import lombok.Data;

import java.util.List;

@Data
public class AttachPLTRequest {

    private String  workspaceName;
    private int uwYear;
    private long workspaceId;
    private long accumulationPackageId;
    private List<PLTAttachingInfo> pltList;
}
