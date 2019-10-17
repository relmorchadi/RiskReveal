package com.scor.rr.importBatch.processing.elt;

import com.scor.rr.importBatch.processing.domain.loss.EventLoss;

import java.util.List;
import java.util.Map;

/**
 * Created by U002629 on 19/02/2015.
 */
interface ELTReader {
    List<EventLoss> readELTs(String[] elts);
    Map<Integer, EventLoss> aggregateELTsAndBuildBetas(String[] elts);
    Map<Integer, EventLoss> aggregateELTsAndBuildBetas(List<EventLoss> events);
}
