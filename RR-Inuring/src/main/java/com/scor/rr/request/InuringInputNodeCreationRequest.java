package com.scor.rr.request;

import lombok.Data;

import java.util.List;

/**
 * Created by u004602 on 11/09/2019.
 */

public class InuringInputNodeCreationRequest {
    private long inuringPackageId;
    private String inputNodeName;
    private List<Long> attachedPLTs;

    public InuringInputNodeCreationRequest(long inuringPackageId, String inputNodeName, List<Long> attachedPLTs) {
        this.inuringPackageId = inuringPackageId;
        this.inputNodeName = inputNodeName;
        this.attachedPLTs = attachedPLTs;
    }

    public InuringInputNodeCreationRequest() {
    }

    public long getInuringPackageId() {
        return inuringPackageId;
    }

    public String getInputNodeName() {
        return inputNodeName;
    }

    public List<Long> getAttachedPLTs() {
        return attachedPLTs;
    }
}
