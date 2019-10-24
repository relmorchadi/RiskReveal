package com.scor.rr.request;

import lombok.Data;

import java.util.List;

/**
 * Created by u004602 on 11/09/2019.
 */
@Data
public class InuringInputNodeCreationRequest {
    private int inuringPackageId;
    private String inputNodeName;
    private List<Integer> attachedPLTs;

    public InuringInputNodeCreationRequest(int inuringPackageId, String inputNodeName, List<Integer> attachedPLTs) {
        this.inuringPackageId = inuringPackageId;
        this.inputNodeName = inputNodeName;
        this.attachedPLTs = attachedPLTs;
    }
}
