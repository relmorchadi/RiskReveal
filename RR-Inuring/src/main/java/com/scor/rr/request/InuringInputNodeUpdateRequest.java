package com.scor.rr.request;

import java.util.List;

/**
 * Created by u004602 on 12/09/2019.
 */
public class InuringInputNodeUpdateRequest {
    private long inuringInputNodeId;
    private String inputNodeName;
    private List<Long> attachedPLTs;

    public InuringInputNodeUpdateRequest(long inuringInputNodeId, String inputNodeName) {
        this(inuringInputNodeId, inputNodeName, null);
    }

    public InuringInputNodeUpdateRequest(long inuringInputNodeId, List<Long> attachedPLTs) {
        this(inuringInputNodeId, null, attachedPLTs);
    }

    public InuringInputNodeUpdateRequest(long inuringInputNodeId, String inputNodeName, List<Long> attachedPLTs) {
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

    public List<Long> getAttachedPLTs() {
        return attachedPLTs;
    }
}
