package com.scor.rr.request;

import com.scor.rr.enums.InuringNodeType;
import lombok.Data;

/**
 * Created by u004602 on 16/09/2019.
 */
@Data
public class InuringEdgeCreationRequest {
    private int inuringPackageId;
    private int sourceNodeId;
    private InuringNodeType sourceNodeType;
    private int targetNodeId;
    private InuringNodeType targetNodeType;

}
