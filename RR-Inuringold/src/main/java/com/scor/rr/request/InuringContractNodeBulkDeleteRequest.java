package com.scor.rr.request;

import com.scor.rr.entity.InuringContractNode;
import lombok.Data;

import java.util.List;
@Data
public class InuringContractNodeBulkDeleteRequest {

    private List<InuringContractNode> inuringContractNodes;
}
