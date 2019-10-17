package com.scor.rr.importBatch.processing.adjustment;

import java.util.Map;

/**
 * Created by U002629 on 03/03/2016.
 */
public interface AdjustmentProcessor {

    int processTree(String treeId);

    void setNamingProperties(Map<String, Object> properties);

}
