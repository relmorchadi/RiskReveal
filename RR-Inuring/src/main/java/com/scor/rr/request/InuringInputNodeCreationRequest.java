package com.scor.rr.request;

import lombok.Data;

import java.util.List;

/**
 * Created by u004602 on 11/09/2019.
 */

public class InuringInputNodeCreationRequest {
    private long inuringPackageId;
    private String inputNodeName;
    private List<Integer> attachedPLTs;

    public InuringInputNodeCreationRequest(long inuringPackageId, String inputNodeName, List<Integer> attachedPLTs) {
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

    public List<Integer> getAttachedPLTs() {
        return attachedPLTs;
    }
}
