package com.scor.rr.request;

import com.scor.rr.enums.InuringNodeType;
import lombok.Data;


public class InvalidateNodeRequest {

    private InuringNodeType inuringNodeType;
    private long nodeId;

    public InvalidateNodeRequest(InuringNodeType inuringNodeType, long nodeId) {
        this.inuringNodeType = inuringNodeType;
        this.nodeId = nodeId;
    }

    public InvalidateNodeRequest() {
    }

    public InuringNodeType getInuringNodeType() {
        return inuringNodeType;
    }

    public long getNodeId() {
        return nodeId;
    }
}
