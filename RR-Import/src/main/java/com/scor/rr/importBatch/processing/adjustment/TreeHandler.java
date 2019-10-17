package com.scor.rr.importBatch.processing.adjustment;

import java.util.List;

/**
 * Created by u004119 on 25/07/2016.
 */
public interface TreeHandler {

    boolean updateTree(String json);

    boolean adjustDefault(String projectId,
                          String pltHeaderId,
                          Long analysisId,
                          String analysisName,
                          Long rdmId,
                          String rdmName,
                          Integer targetRapId,
                          String sourceConfigVendor);

    //List<Node> parseTree(String structureId);

    void preparePendingNodes(String structureId, List<String> nodeIds);

    void setCalculationRequestCallback(CalculationRequestCallback callback);

}
