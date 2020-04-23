package com.scor.rr.domain.requests.ScopeAndCompleteness;

import lombok.Data;

import java.util.List;

@Data
public class AttachPLTRequest {

    private long projectId;
    private String  workspaceName;
    private int uwYear;
    private long accumulationPackageId;
    private List<PLTAttachingInfo> pltList;
    private long projectId;
}
