package com.scor.rr.request;

import java.util.List;

/**
 * Created by u004602 on 12/09/2019.
 */
public class InuringInputNodeUpdateRequest {
    private int inuringInputNodeId;
    private String inputNodeName;
    private List<Integer> attachedPLTs;

    public InuringInputNodeUpdateRequest(int inuringInputNodeId, String inputNodeName) {
        this(inuringInputNodeId, inputNodeName, null);
    }

    public InuringInputNodeUpdateRequest(int inuringInputNodeId, List<Integer> attachedPLTs) {
        this(inuringInputNodeId, null, attachedPLTs);
    }

    public InuringInputNodeUpdateRequest(int inuringInputNodeId, String inputNodeName, List<Integer> attachedPLTs) {
        this.inuringInputNodeId = inuringInputNodeId;
        this.inputNodeName = inputNodeName;
        this.attachedPLTs = attachedPLTs;
    }

    public int getInuringInputNodeId() {
        return inuringInputNodeId;
    }

    public String getInputNodeName() {
        return inputNodeName;
    }

    public List<Integer> getAttachedPLTs() {
        return attachedPLTs;
    }
}
