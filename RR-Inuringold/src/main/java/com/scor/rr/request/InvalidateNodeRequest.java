package com.scor.rr.request;

import com.scor.rr.enums.InuringNodeType;
import lombok.Data;


public class InvalidateNodeRequest {

    private InuringNodeType inuringNodeType;
    private int nodeId;

    public InvalidateNodeRequest(InuringNodeType inuringNodeType, int nodeId) {
        this.inuringNodeType = inuringNodeType;
        this.nodeId = nodeId;
    }

    public InvalidateNodeRequest() {
    }

    public InuringNodeType getInuringNodeType() {
        return inuringNodeType;
    }

    public int getNodeId() {
        return nodeId;
    }
}
