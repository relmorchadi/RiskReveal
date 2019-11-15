package com.scor.rr.request;

import com.scor.rr.enums.InuringNodeType;
import lombok.Data;

/**
 * Created by u004602 on 16/09/2019.
 */

public class InuringEdgeCreationRequest {
    private long inuringPackageId;
    private long sourceNodeId;
    private InuringNodeType sourceNodeType;
    private long targetNodeId;
    private InuringNodeType targetNodeType;

    public InuringEdgeCreationRequest(long inuringPackageId, long sourceNodeId, InuringNodeType sourceNodeType, long targetNodeId, InuringNodeType targetNodeType) {
        this.inuringPackageId = inuringPackageId;
        this.sourceNodeId = sourceNodeId;
        this.sourceNodeType = sourceNodeType;
        this.targetNodeId = targetNodeId;
        this.targetNodeType = targetNodeType;
    }

    public InuringEdgeCreationRequest() {
    }

    public long getInuringPackageId() {
        return inuringPackageId;
    }

    public long getSourceNodeId() {
        return sourceNodeId;
    }

    public InuringNodeType getSourceNodeType() {
        return sourceNodeType;
    }

    public long getTargetNodeId() {
        return targetNodeId;
    }

    public InuringNodeType getTargetNodeType() {
        return targetNodeType;
    }
}
