package com.scor.rr.request;

import com.scor.rr.entity.InuringInputNode;

import java.util.List;

public class InuringInputNodeBulkDeleteRequest {

    private List<InuringInputNode> inuringInputNodes;

    public InuringInputNodeBulkDeleteRequest(List<InuringInputNode> inuringInputNodes) {

        this.inuringInputNodes = inuringInputNodes;
    }

    public InuringInputNodeBulkDeleteRequest() {
    }

    public List<InuringInputNode> getInuringInputNodes() {
        return inuringInputNodes;
    }
}
