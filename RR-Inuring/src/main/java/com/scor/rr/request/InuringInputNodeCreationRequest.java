package com.scor.rr.request;

import java.util.List;

/**
 * Created by u004602 on 11/09/2019.
 */
public class InuringInputNodeCreationRequest {
    private int inuringPackageId;
    private String inputNodeName;
    private List<Integer> attachedPLTs;

    public InuringInputNodeCreationRequest(int inuringPackageId, String inputNodeName, List<Integer> attachedPLTs) {
        this.inuringPackageId = inuringPackageId;
        this.inputNodeName = inputNodeName;
        this.attachedPLTs = attachedPLTs;
    }

    public int getInuringPackageId() {
        return inuringPackageId;
    }

    public String getInputNodeName() {
        return inputNodeName;
    }

    public List<Integer> getAttachedPLTs() {
        return attachedPLTs;
    }
}
