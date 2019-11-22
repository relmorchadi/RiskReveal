package com.scor.rr.request;

import java.util.List;

/**
 * Created by u004602 on 12/09/2019.
 */
public class InuringInputNodeUpdateRequest {
    private long inuringInputNodeId;
    private String inputNodeName;
    private List<Integer> attachedPLTs;

    public InuringInputNodeUpdateRequest(long inuringInputNodeId, String inputNodeName) {
        this(inuringInputNodeId, inputNodeName, null);
    }

    public InuringInputNodeUpdateRequest(long inuringInputNodeId, List<Integer> attachedPLTs) {
        this(inuringInputNodeId, null, attachedPLTs);
    }

    public InuringInputNodeUpdateRequest(long inuringInputNodeId, String inputNodeName, List<Integer> attachedPLTs) {
        this.inuringInputNodeId = inuringInputNodeId;
        this.inputNodeName = inputNodeName;
        this.attachedPLTs = attachedPLTs;
    }

    public InuringInputNodeUpdateRequest() {
    }

    public long getInuringInputNodeId() {
        return inuringInputNodeId;
    }

    public String getInputNodeName() {
        return inputNodeName;
    }

    public List<Integer> getAttachedPLTs() {
        return attachedPLTs;
    }
}
