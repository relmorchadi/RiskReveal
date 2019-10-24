package com.scor.rr.request;

import com.scor.rr.enums.InuringNodeType;
import lombok.Data;

/**
 * Created by u004602 on 16/09/2019.
 */

public class InuringEdgeCreationRequest {
    private int inuringPackageId;
    private int sourceNodeId;
    private InuringNodeType sourceNodeType;
    private int targetNodeId;
    private InuringNodeType targetNodeType;

    public InuringEdgeCreationRequest(int inuringPackageId, int sourceNodeId, InuringNodeType sourceNodeType, int targetNodeId, InuringNodeType targetNodeType) {
        this.inuringPackageId = inuringPackageId;
        this.sourceNodeId = sourceNodeId;
        this.sourceNodeType = sourceNodeType;
        this.targetNodeId = targetNodeId;
        this.targetNodeType = targetNodeType;
    }

    public InuringEdgeCreationRequest() {
    }

    public int getInuringPackageId() {
        return inuringPackageId;
    }

    public int getSourceNodeId() {
        return sourceNodeId;
    }

    public InuringNodeType getSourceNodeType() {
        return sourceNodeType;
    }

    public int getTargetNodeId() {
        return targetNodeId;
    }

    public InuringNodeType getTargetNodeType() {
        return targetNodeType;
    }
}
