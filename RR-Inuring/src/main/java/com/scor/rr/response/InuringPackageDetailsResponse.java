package com.scor.rr.response;

import com.scor.rr.entity.*;
import lombok.Data;
import org.dbunit.util.search.Edge;

import java.util.List;
@Data
public class InuringPackageDetailsResponse {

    private InuringPackage inuringPackage;
    private List<InuringInputNode> inputNodes;
    private List<InuringEdge> edges;
    private List<InuringContractNode> inuringContractNodes;
    private List<InuringCanvasLayout> inuringCanvasLayouts;
    private InuringFinalNode inuringFinalNode;


}
