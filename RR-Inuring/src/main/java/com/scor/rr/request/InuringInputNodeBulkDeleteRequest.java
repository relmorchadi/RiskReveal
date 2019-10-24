package com.scor.rr.request;

import com.scor.rr.entity.InuringInputNode;
import lombok.Data;

import java.util.List;

@Data
public class InuringInputNodeBulkDeleteRequest {

    private List<InuringInputNode> inuringInputNodes;

}
