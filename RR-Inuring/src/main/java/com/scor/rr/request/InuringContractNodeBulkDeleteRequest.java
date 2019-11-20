package com.scor.rr.request;

import com.scor.rr.entity.InuringContractNode;
import lombok.Data;

import java.util.List;

public class InuringContractNodeBulkDeleteRequest {

    private List<InuringContractNode> inuringContractNodes;

    public InuringContractNodeBulkDeleteRequest(List<InuringContractNode> inuringContractNodes) {
        this.inuringContractNodes = inuringContractNodes;
    }

    public InuringContractNodeBulkDeleteRequest() {
    }

    public List<InuringContractNode> getInuringContractNodes() {
        return inuringContractNodes;
    }
}
